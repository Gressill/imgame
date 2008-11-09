package net.imggame.model {
	
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import flash.net.Socket;			//导入类包
	
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public final class ModelLocator implements IModelLocator {
		
		private static var instance:ModelLocator;

		public function ModelLocator(access:SingletonEnforcer) {
			
			if (access == null) {
			    throw new CairngormError( CairngormMessageCodes.SINGLETON_EXCEPTION, "ModelLocator" );
			}
			
			instance = this;
		}
		 
		public static function getInstance() : ModelLocator {
			
			if (instance == null) {
				instance = new ModelLocator( new SingletonEnforcer() );
			}
			return instance;
			
		}
		
		public var socket:Socket=new Socket();//定义socket
		
		/*
		*	Variables
		*/
		public var workflowState:uint 			= ModelLocator.CHOOSE_MODEL;
		public var ImgPriceData:ArrayCollection	= new ArrayCollection;//init history price array
		public var OtherGamePriceData:ArrayCollection = new ArrayCollection;
		
		public var ImgTempData:int = new int;
		public var ImgLevel:String = new String;
		public var ImgAgentsNum:String = new String;
		public var playerNum:String = new String;
		
		//humenagent
		public var ImgScore:int = new int;
		public var ImgTuennum:int = new int; 
		public var ImgAvgScore:String = new String;   //every step humenagent's score
		
		//mgagents 
		public var ImgBest:int = new int;
		public var ImgAvg:int = new int;
		public var ImgWorst:int = new int;
		public var low:int = new int;
		
		
		public static const serverIp:String = "127.0.0.1";
		public static const serverPort:int = 8888;
		/*
		*	Static Variables
		*/
		public static const IMG_MODEL:uint 			= 0;
		public static const OTHER_MODEL:uint 		= 1;
		public static const CHOOSE_MODEL:uint 		= 2;
		public static const INIT_PRICE_DATA:uint	= 25; //the initial historical price data.

	}
}

class SingletonEnforcer {}