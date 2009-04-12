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
			
			Alert.show(str.toString());
//			if(str=="")
//				return;
//        	
//        	rexml =new XML(str);
//        	
//        	renode = rexml.m;
//        	switch(renode.toString())
//        	{
//        		case "nstart": 
//        			Alert.show("need to start game");
//        			break;
//        		case "start":
//        			 
//					 priceList = rexml.p.h;
//					 turnList  = rexml.t;
//					 volList   = rexml.we.v;
//					 scoreList = rexml.r;
//					 bestwealth   = rexml.b;
//					 worstwealth  = rexml.w;
//					
//					this.turn = turnList[0];
//					this.b_wealth = bestwealth[0];
//					this.w_wealth = worstwealth[0];
//					this.score    = scoreList[0];
//					//s,m,n
//					this.s = rexml.s[0];
//					this.m = rexml.m1[0];
//					this.n = rexml.n[0];
//					
//					ImgPriceData.removeAll();
//					ImgVolatility.removeAll();
//					
//					this.averageprice = 0;
//					
//					for(var i:uint=0; i<priceList.length(); i++){
//
//						if(i<MA)
//						{
//							this.averageprice = this.averageprice + priceList[i]/MA;
//							ImgPriceData.addItem({"price":priceList[i],"ma5":this.averageprice});
//						}
//						else
//						{
//							this.averageprice = this.averageprice + priceList[i]/MA- priceList[i-MA]/MA;
//							ImgPriceData.addItem({"price":priceList[i],"ma5":this.averageprice});
//						}
//						ImgVolatility.addItem(volList[i] / 100);
//					}
//					break;
//				case "play":
//					this.turn = this.turn + 1;
//					 var newprice:XMLList 	= rexml.h;
//					 var newvol:XMLList		= rexml.v;
//					 bestwealth     = rexml.b;
//					 worstwealth    = rexml.w;
//					 scoreList      = rexml.r;
//					this.averageprice 		= newprice[0] / MA;
//					for(var u:uint=0;u<MA-1;u++)
//						this.averageprice += ImgPriceData.getItemAt(ImgPriceData.length -1-u).price/MA;
//					ImgPriceData.addItem({"price":newprice[0],"ma5":this.averageprice});
//					ImgVolatility.addItem(newvol[0] / 100);
//					
//					this.b_wealth = bestwealth[0];
//					this.w_wealth = worstwealth[0];
//					this.score    = scoreList[0];
//					break;
//				default:
//					break;
//        	}
        	
        	model.action_active = true;
		}
		
		public function parseResultSerialize(obj:Object):void
		{
			try{
				ValueLocator.ImgPriceData.addItem((obj.price));
					            
	            /*
	            //var a:int = this.ImgPriceData[this.ImgPriceData.length-1];
	            if( int(obj.price)>0 )
	            {
	            	this.score = this.score - ((int)(obj.price)-a);
	            }else
	            {
	            	this.score = this.score + ((int)(obj.price)-a);
	            }
	            //this.obj = null;
	            //Alert.show("ImgPriceData="+ImgPriceData.toString());
	            */
	            Alert.show("obj price"+obj.price);
			}catch(e:Error){
				Alert.show("error is:"+e.getStackTrace()+e.toString());
			}finally
			{
				model.action_active = true;
			}
			
		}

		/*
		*	Variables
		*/
		public static var ImgPriceData:ArrayCollection	= new ArrayCollection;//init history price array
		public static var ImgVolatility:ArrayCollection = new ArrayCollection;//price volatility 
		public var turn:uint=0;
		public var b_wealth:Number=0;
		public var w_wealth:Number=0;
		public var score:Number=0;
		
		public var averageprice:Number = 0;
		public var Incprice:Array = new Array();
		public var testString:String=null;
		public var s:uint = 0;
		public var m:uint = 0;
		public var n:uint = 0;
		public var cl:String = new String("hard");
		
		public var rexml:XML;
        public var renode:XMLList;
        public var priceList:XMLList;
        public var turnList:XMLList;
        public var volList:XMLList;
        public var bestwealth:XMLList;
        public var worstwealth:XMLList;
        public var scoreList:XMLList;
        
        private var a:uint = 0;
		/*
		* Static Variables
		*/
		static private var MA:uint = 10;
		static private var TW:uint = 5;
	}
}

class SingletonEnforcer_2{}