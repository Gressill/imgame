package net.imggame.model {
	
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import mx.collections.ArrayCollection;
	
	import net.imggame.business.Imgsocket;
	import net.imggame.vo.*;
	
	
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
		
		/*
		*	Variables
		*/
		public var workflowState:uint 					= ModelLocator.CHOOSE_MODEL; /*Model Locator */
		public var ImgPriceData:ArrayCollection			= new ArrayCollection;       /*Price Data*/
		public var gameParam:ParamPost;												 /*game Param: m,n,s*/
		public var _socket:Imgsocket;												 /*socket connector*/
		public var _connect:String;													 /*connect hander*/
		public var username:String;													 /*the game user name*/
		public var start_active:Boolean = true;										 //
		public var action_active:Boolean = false;
		public var stop_active:Boolean = false;
		/*
		*	Static Variables
		*/
		public static const IMG_MODEL:uint 			= 0;
		public static const OTHER_MODEL:uint 		= 1;
		public static const CHOOSE_MODEL:uint 		= 2;
		public static const INIT_PRICE_DATA:uint	= 0; //the initial historical price data.
		
		public static const XML_SERIALIZE_TAG:String = "SERIALIZE";
		public static const SERVER_IP:String = "192.168.1.111";
	}
}

class SingletonEnforcer {}