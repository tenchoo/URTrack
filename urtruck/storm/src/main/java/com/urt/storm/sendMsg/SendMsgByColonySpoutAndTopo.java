package com.urt.storm.sendMsg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.urt.storm.batchQueryTraffic.QueryTrafficBolt;
import com.urt.storm.util.ZookeeperClient;

public class SendMsgByColonySpoutAndTopo {

	//汇报offset信息的root路径
	private static final String offsetZkRoot = "/archiveZkRoot";
    //存储该spout id的消费offset信息,譬如以topoName来命名
	private static final String offsetZkId = "sendMsgByColonyProducer";
	//topicName
    private static final String TOPICS = "sendMsgByColonyProducer";
    
    private static String host = "";
    private static Long STARTOFFSETTIME = -1L;
    private static String offsetZkPort = "";
    private static List<String> zkServersList = new ArrayList<String>();
    private static String brokerList = "";
    private static String serializerClass = "kafka.serializer.StringEncoder";
    
    static{
    	Properties prop = new Properties();
    	InputStream in = ClassLoader.getSystemResourceAsStream("application.properties"); 
        try {
            prop.load(in);
            host = prop.getProperty("zkHost").trim();
            STARTOFFSETTIME = Long.parseLong(prop.getProperty("startOffsetTime").trim());
            offsetZkPort = prop.getProperty("zkPort").trim();
            String offsetZkServers = prop.getProperty("offsetZkServers").trim();
            for(int i = 0 ;i < offsetZkServers.split(",").length; i++){
            	zkServersList.add(offsetZkServers.split(",")[i]);
            }
            brokerList = prop.getProperty("metadata.broker.list").trim();
            serializerClass = prop.getProperty("serializer.class").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 功能描述：等待指令停止Topology
     * @author zhouzy3
     * @date 2016年7月4日 下午2:25:38
     * @param  
     * @return void
     */
    private static void stopWaitingForInput() {
        try {
            System.out.println("PRESS ENTER TO STOP");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.exit(0);
            System.out.println("STORM IS ALREADY STOP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：初始化storm配置
     * @author zhouzy3
     * @date 2016年7月4日 下午2:26:02
     * @param @return 
     * @return Config
     */
    protected static Config getConfig() {
        Config config = new Config();
		Map<String, String> map = new HashMap<String, String>();
		map.put("metadata.broker.list", brokerList);
		map.put("serializer.class", serializerClass);
		config.put("kafka.broker.properties", map);
		config.put("topic",TOPICS);
        config.setDebug(true);
        return config;
    }

    /**
     * 功能描述：单机模式（用于进行开发测试）和集群模式（线上运行）
     * @author zhouzy3
     * @date 2016年7月4日 下午2:26:14
     * @param @param args
     * @param @throws Exception 
     * @return void
     */
    public static void main(String[] args) throws Exception {
    	
        if (args != null && args.length>0) {
        	//集群模式
        	submitTopologyRemoteCluster(args[0], getTopolgyKafkaSpout(), getConfig());
        } else {
        	//单机模式
        	submitTopologyLocalCluster(getTopolgyKafkaSpout(), getConfig());
        }
    }

    /**
     * 功能描述：集群模式启动
     * @author zhouzy3
     * @date 2016年7月4日 下午2:26:25
     * @param @param arg
     * @param @param topology
     * @param @param config
     * @param @throws Exception 
     * @return void
     */
    protected static void submitTopologyRemoteCluster(String arg, StormTopology topology, Config config) throws Exception {
    	config.setNumWorkers(3);
    	config.put(Config.TOPOLOGY_ACKER_EXECUTORS, 40);//设置acker的数量 
    	config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 200);//设置一个spout task上面最多有多少个没有处理的tuple（没有ack/failed）回复，以防止tuple队列爆掉 
    	config.put(Config.TOPOLOGY_BACKPRESSURE_ENABLE, false);
    	config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384);
    	config.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384);
    	StormSubmitter.submitTopology(arg, config, topology);
    }
    
	  /**
	   * 功能描述：单机模式启动
	   * @author zhouzy3
	   * @date 2016年7月4日 下午2:26:34
	   * @param @param topology
	   * @param @param config
	   * @param @throws InterruptedException 
	   * @return void
	   */
    protected static void submitTopologyLocalCluster(StormTopology topology, Config config) throws InterruptedException {
        LocalCluster cluster = new LocalCluster();
        config.setMaxTaskParallelism(3);
        cluster.submitTopology("archiveTopo", config, topology);
        stopWaitingForInput();
    }
    
    /**
     * 功能描述：初始化kafka配置
     * @author zhouzy3
     * @date 2016年7月4日 下午2:26:48
     * @param @return 
     * @return SpoutConfig
     */
    public static SpoutConfig getSpoutConfig(){
    	// 配置Zookeeper地址
        BrokerHosts brokerHosts = new ZkHosts(host);
        // 配置Kafka订阅的Topic，以及zookeeper中数据节点目录和名字
        ZookeeperClient client = new ZookeeperClient();
        Stat stat = null;
		try {
			// 连接zookeeper
			client.connectZookeeper(host);
			stat = client.exists(offsetZkRoot, false);
			 if(stat == null){
				 client.createNode(offsetZkRoot, offsetZkId.getBytes());
			 }
			// client.closeConnect();
		} catch (IOException | KeeperException | InterruptedException e) {
			
			e.printStackTrace();
		}
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TOPICS, offsetZkRoot, offsetZkId);
        spoutConfig.zkRoot = offsetZkRoot;
        spoutConfig.zkPort = Integer.parseInt(offsetZkPort);
        spoutConfig.zkServers = zkServersList;
        spoutConfig.id = offsetZkId;
        spoutConfig.ignoreZkOffsets=true;
        spoutConfig.startOffsetTime=STARTOFFSETTIME;
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());  
    	return spoutConfig;
    }
    
    /**
     * 功能描述：Storm调用程序
     * @author zhouzy3
     * @date 2016年7月4日 下午2:27:07
     * @param @return 
     * @return StormTopology
     */
    public static StormTopology getTopolgyKafkaSpout() {
    	final TopologyBuilder tBuilder = new TopologyBuilder();
        tBuilder.setSpout("sendMsgTotalSpout", new KafkaSpout(getSpoutConfig()), 3);
        tBuilder.setBolt("sendMsgTotalBolt", new SendMsgByColonyBolt(),15).setNumTasks(10).shuffleGrouping("sendMsgTotalSpout");
        return tBuilder.createTopology();
    }
    
}

