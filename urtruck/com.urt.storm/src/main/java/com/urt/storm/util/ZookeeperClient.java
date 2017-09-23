package com.urt.storm.util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 类说明：zookeeper创建、修改、删除节点工具类
 * @author fuhp3
 * @date 2016年7月5日 上午9:54:44
 */
public class ZookeeperClient implements Watcher {
	public ZooKeeper zookeeper;
	private static int SESSION_TIME_OUT = 2000;
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	/**
	 * 功能描述：连接zookeeper
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:56:35
	 * @param @param host
	 * @param @throws IOException
	 * @param @throws InterruptedException
	 * @return void
	 */
	public void connectZookeeper(String host) throws IOException,
			InterruptedException {
		zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
		countDownLatch.await();
		System.out.println("zookeeper connect ok");
	}

	/**
	 * 功能描述：实现watcher的接口方法，当连接zookeeper成功后，zookeeper会通过此方法通知watcher
	 * 此处为如果接受到连接成功的event，则countDown，让当前线程继续其他事情。
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:57:14
	 * @param @param event
	 */
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			System.out.println("watcher receiver event");
			countDownLatch.countDown();
		}
	}

	/**
	 * 功能描述：根据路径创建节点，并且设置节点数据
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:57:34
	 * @param @param path
	 * @param @param data
	 * @param @return
	 * @param @throws KeeperException
	 * @param @throws InterruptedException
	 * @return String
	 */
	public String createNode(String path, byte[] data) throws KeeperException,
			InterruptedException {
		return this.zookeeper.create(path, data, Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
	}

	/**
	 * 功能描述：根据路径获取所有孩子节点
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:57:45
	 * @param @param path
	 * @param @return
	 * @param @throws KeeperException
	 * @param @throws InterruptedException
	 * @return List<String>
	 */
	public List<String> getChildren(String path) throws KeeperException,
			InterruptedException {
		return this.zookeeper.getChildren(path, false);
	}

	/**
	 * 功能描述：修改节点数据
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:58:40
	 * @param @param path
	 * @param @param data
	 * @param @param version
	 * @param @return
	 * @param @throws KeeperException
	 * @param @throws InterruptedException
	 * @return Stat
	 */
	public Stat setData(String path, byte[] data, int version)
			throws KeeperException, InterruptedException {
		return this.zookeeper.setData(path, data, version);
	}
	
	/**
	 * 功能描述：判断节点是否存在
	 * @author zhouzy3
	 * @date 2016年7月5日 上午10:19:36
	 * @param @param path
	 * @param @param watch
	 * @param @return
	 * @param @throws KeeperException
	 * @param @throws InterruptedException 
	 * @return Stat
	 */
	public Stat exists(String path, boolean watch) throws KeeperException, InterruptedException{
		return this.zookeeper.exists(path, watch);
	}

	/**
	 * 功能描述：根据路径获取节点数据
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:57:57
	 * @param @param path
	 * @param @return
	 * @param @throws KeeperException
	 * @param @throws InterruptedException
	 * @return byte[]
	 */
	public byte[] getData(String path) throws KeeperException,
			InterruptedException {
		return this.zookeeper.getData(path, false, null);
	}

	/**
	 * 功能描述：删除节点
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:58:06
	 * @param @param path
	 * @param @param version
	 * @param @throws InterruptedException
	 * @param @throws KeeperException
	 * @return void
	 */
	public void deleteNode(String path, int version)
			throws InterruptedException, KeeperException {
		this.zookeeper.delete(path, version);
	}

	/**
	 * 功能描述：关闭zookeeper连接
	 * 
	 * @author zhouzy3
	 * @date 2016年7月5日 上午9:58:15
	 * @param @throws InterruptedException
	 * @return void
	 */
	public void closeConnect() throws InterruptedException {
		if (null != zookeeper) {
			zookeeper.close();
		}
	}
}
