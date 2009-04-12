package net.imggame.model {
	
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import flash.system.Security;
	
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
		public var workflowState:uint 			= ModelLocator.CHOOSE_MODEL;
		public var ImgPriceData:ArrayCollection	= new ArrayCollection;//init history price array
		public var OtherGamePriceData:ArrayCollection = new ArrayCollection;
		public var gameParam:ParamPost;
		public var _socket:Imgsocket;
		public var _connect:String;
		public var start_active:Boolean = true;
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
		//public static const XML_SERIALIZE_TAG:String = "XML";
		
		public static const GAME_INFO:String        = "Minority Game is a game proposed by the University of Fribourg. It is inspired by the El Farol bar problem, which is a simple model that shows how (selfish) players cooperate with each other in the absence of communication. In the Minority Game, an odd number of players each have to choose one of two choices independently at each turn. The players who end up on the minority side win.";

	}
}

class SingletonEnforcer {}