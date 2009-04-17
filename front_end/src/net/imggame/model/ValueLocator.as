package net.imggame.model {
	
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	[Bindable]
	public final class ValueLocator implements IModelLocator {
		
		private static var instance:ValueLocator;
		public var model:ModelLocator = ModelLocator.getInstance();

		
		public function ValueLocator(access:SingletonEnforcer_2) {
			
			if(access == null) {
			    throw new CairngormError( CairngormMessageCodes.SINGLETON_EXCEPTION, "ValueLocator" );
			}
			
			instance = this;
		}
		 
		public static function getInstance() : ValueLocator {
			
			if (instance == null) {
				instance = new ValueLocator( new SingletonEnforcer_2() );
			}
			
			return instance;
			
		}
		public function parseResultXml ( str:String ):void {
			
			//Alert.show(str.toString());
        	
        	model.action_active = true;
		}
		
		public function parseResultSerialize(returnObj:Object):void
		{
			try{//according to the returnObj.event to make different 
				switch (returnObj.event){
				case "startAction":
					//Alert.show(returnObj.historyPrice as DataGrid);
					turn=0;
					ImgPriceData.removeAll();
					for(var i:uint=0; i<returnObj.historyPrice.length; i++){
						ImgPriceData.addItem({"price":returnObj.historyPrice[i]});
					}
					break;
				case "buyAction":
				case "sellAction":
					ImgPriceData.addItem({"price":returnObj.price});
					//Alert.show(returnObj.bestAgentScore);
					BestScore = returnObj.bestAgentScore;
					AvgScore  = returnObj.avgAgentScore;
					WorstScore= returnObj.worseAgentScore;
					myScore   = returnObj.myScore;
					avgScore  = returnObj.avgScore;
					lastBuyNum= returnObj.lastBuyNum;
					lastSellNum= returnObj.lastSellNum;
					if(returnObj.event == "buyAction"){
						tradeInfo = "last trade(buys:"+lastBuyNum+"+1"+",sells:"+lastSellNum+"); your P/L: "+myScore;
					}else if(returnObj.event == "sellAction"){
						tradeInfo = "last trade(buys:"+lastBuyNum+",sells:"+lastSellNum+"+1"+"); your P/L: "+myScore;
					}
					if(returnObj.permision==true){
						permision = "Able to submit";
					}else{
						permision = "Unable to submit, try to play again.";
					}
					turn = turn +1;
					break;
				default:
				    break;
				}
			}catch(e:Error){
				Alert.show("Price display error is:"+e.getStackTrace()+e.toString());
			}finally
			{
				model.action_active = true;
			}
			
		}

		/*
		*	Variables
		*/

		public  var ImgPriceData:ArrayCollection	= new ArrayCollection;//init history price array
		public  var BestScore:int;
		public  var AvgScore:int;
		public  var WorstScore:int;
		public  var BestHumanScore:int;
		public  var AvgHumanScore:int;
		public  var WorstHumanScore:int;
		public  var myScore:int;
		public  var avgScore:int;
		public  var turn:int;
		public  var permision:String = new String("Able to submit");
		public  var connectMessage:String = new String("Disconnecting to the server.");
		public  var lastBuyNum:int;
		public  var lastSellNum:int;
		public  var tradeInfo:String = new String("last trade(buys:0+1,sells:0); your P/L: 0");
		public  var level:String;
	}
}

class SingletonEnforcer_2{}