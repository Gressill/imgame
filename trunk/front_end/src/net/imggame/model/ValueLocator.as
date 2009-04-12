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
		
		public function parseResultSerialize(obj:Object):void
		{
			try{
				ValueLocator.ImgPriceData.addItem({"price":obj.price});
					            
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
	            //Alert.show("obj price"+obj.price);
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

		public static var ImgPriceData:ArrayCollection	= new ArrayCollection;//init history price array

	}
}

class SingletonEnforcer_2{}