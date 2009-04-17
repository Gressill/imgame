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
				Alert.show("Error is:"+e.getStackTrace()+e.toString());
			}
		}
		private function configureListeners():void{
			//ProgressEvent.s
			this.addEventListener(Event.CONNECT,onConnect);
			this.addEventListener(Event.CLOSE,onClose);
		        
	        if(ModelLocator.XML_SERIALIZE_TAG == "XML")
			{
				//Alert.show("XML");
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
			//Alert.show( "SecurityErrorEvent: \n"+e.toString() );
		}
		
		protected function onConnect(event:Event):void{
			//Alert.show("onConnect\n");
			valuelocator.connectMessage = new String("Connectting to the server.");
		}
		
		protected function onClose(event:Event):void{
			 //Alert.show("onClose \n");
			valuelocator.connectMessage = new String("Disconnecting to the server.");
			this.close();
		}
		
    	public function socketDataHandlerXml( event:ProgressEvent ):void{
			
	    }
			
		public function ioErrorHandler(event:IOErrorEvent):void {
			trace("IoErrorHandler： " + event);
		}
		
		public function socketDataHandlerSerialize(event:ProgressEvent):void {
		
			obj=modellocator._socket.readObject();
       		
			valuelocator.parseResultSerialize(obj);
			
            this.obj = null;
		
		}
	}
}