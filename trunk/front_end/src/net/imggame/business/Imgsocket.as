package net.imggame.business {
	import flash.errors.*;
	import flash.events.*;
	import flash.net.Socket;
	
	import mx.controls.Alert;
	
	import net.imggame.model.ModelLocator;
	import net.imggame.model.ValueLocator;
	
	public class Imgsocket extends Socket {
		private var response:String = null;
		private var valuelocator:ValueLocator = ValueLocator.getInstance();
		private var modellocator:ModelLocator = ModelLocator.getInstance();
		
		private var str:String="";
		
        private var requestObj:Object;
		private var obj:Object=new Object();
		
		private var myprocress:ProgressEvent = new ProgressEvent("progress", false, false, 0, 2644);
		
		public function Imgsocket(host:String=null, port:int=0) {
			try{
				super(host, port);
				//ValueLocator.ImgPriceData.removeAll();
				//ValueLocator.ImgPriceData.addItem(ModelLocator.INIT_PRICE_DATA);
				this.configureListeners();
			}catch(e:Error){
				Alert.show("error is:"+e.getStackTrace()+e.toString());
			}
		}
		private function configureListeners():void{
			//ProgressEvent.s
			this.addEventListener(Event.CONNECT,onConnect);
			this.addEventListener(Event.CLOSE,onClose);
		        
	        if(ModelLocator.XML_SERIALIZE_TAG == "XML")
			{
				this.addEventListener(ProgressEvent.SOCKET_DATA,socketDataHandlerXml);
			}
			else if(ModelLocator.XML_SERIALIZE_TAG == "SERIALIZE")
			{
	            this.addEventListener(ProgressEvent.SOCKET_DATA,socketDataHandlerSerialize);
			}
			
			this.addEventListener(SecurityErrorEvent.SECURITY_ERROR,securityErrorHandler);
			this.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			//addEventListener("recieved",recievedData);
			
		}
		
		protected function securityErrorHandler( e:SecurityErrorEvent ):void {
			Alert.show( "SecurityErrorEvent"+e.toString() );
		}
		
		protected function onConnect(event:Event):void{
			//Alert.show("onConnect\n");
            //requestObj = new Object();
            //requestObj = {event:"gameInit",m:model.mermoryNumber,s:model.starategiesNumber,n:model.ImgAgentsNum};
           	// modellocator._socket.writeObject(requestObj);
            //modellocator._socket.flush();
			//this.writeUTFBytes("<test/>");
			//this.flush();
		}
		
		protected function onClose(event:Event):void{
			 //Alert.show("onClose \n");
			this.close();
		}
		
    	public function socketDataHandlerXml( event:ProgressEvent ):void{

    		//Alert.show(this.str.substring(this.str.length-7,this.str.length));
    		Alert.show("Total="+event.bytesTotal.toString()+",loaded="+event.bytesLoaded.toString());
    		//Alert.show(this.bytesAvailable.toString());
    		
//    		try {
//    			this.str = this.str + this.readUTFBytes(this.bytesAvailable).toString();
//    			if(this.str.substring(this.str.length-7,this.str.length)=="</game>")
//    			{
//    				valuelocator.parseResultXml(this.str);
//    				this.str = "";
//    			}
//      		}catch( e:IOError ){
//      			Alert.show("IOError"+e.toString());
//      		}
	    }
			
		public function ioErrorHandler(event:IOErrorEvent):void {
			trace("ioErrorHandler信息： " + event);
		}
		
		
		
		
		public function socketDataHandlerSerialize(event:ProgressEvent):void {
			//Alert.show("接收数据");
			obj=modellocator._socket.readObject();
//			for(var k in obj)
//			{
//			    Alert.show(k+":"+obj[k]);
//			}
////			trace("----------------------------------");
//            Alert.show("111111111111111"+modellocator.ImgPriceData.length);
//          
			valuelocator.parseResultSerialize(obj);
			
//            var a:int = valuelocator.ImgPriceData[valuelocator.ImgPriceData.length-1];
//            
//            valuelocator.ImgPriceData.addItem( obj.price );
//            if( int(obj.price)>0 )
//            {
//            	valuelocator.score -= ((int)(obj.price)-a);
//            }else
//            {
//            	valuelocator.score += ((int)(obj.price)-a);
//            }
            this.obj = null;
//            //Alert.show("a="+a);
//            modellocator.action_active = true;
            //Alert.show("obj.price");
			//this.dispatchEvent(new Event("recieved")); 
			//recievedData();
			//this.dispatchEvent(new Event("recieved"));
		}
		
		
		//接受服务器信息
		//internal function recievedData(evt:Event):void
//		private function recievedData():void
//		{
//
//            var a:int = ValueLocator.ImgPriceData[ValueLocator.ImgPriceData.length-1];
//            ValueLocator.ImgPriceData.addItem( obj.price );
//            if( int(obj.price)>0 )
//            {
//            	valuelocator.score -= ((int)(obj.price)-a);
//            }
//            else
//            {
//            	valuelocator.score += ((int)(obj.price)-a);
//            }
//            obj = null;
//            modellocator.action_active = true;
//            Alert.show("obj.price"+obj.price);
//		}
	}
}