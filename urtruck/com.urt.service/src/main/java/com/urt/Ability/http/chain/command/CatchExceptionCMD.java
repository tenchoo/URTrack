package com.urt.Ability.http.chain.command;


import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.log4j.Logger;


/**
 * 
 * 
 * @author wangxb20
 * 
 * @version 1.0
 */
public class CatchExceptionCMD implements Filter {


	private static final Logger logger = Logger.getLogger(CatchExceptionCMD.class);

	public boolean postprocess(Context ctx, Exception exp) {
		logger.info("CatchExceptionCMD's postprocess() is called!");

		if (exp != null) {
			logger.warn("Catch process exception in commons-chain: ", exp);
			
			if(ctx.get("ResponseMessage")==null){
				
			}			
		}
		return false;
	}

	public boolean execute(Context ctx) throws Exception {
		// do nothing
		return false;
	}

}
