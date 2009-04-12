package net.imggame.command {
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.net.Socket;
	
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;
	
	import net.imggame.business.Imgsocket;
	import net.imggame.model.ModelLocator;
		
	//import mx.controls.Alert;

	public class ImgSellCommand implements ICommand	{
		
		public var model:ModelLocator = ModelLocator.getInstance();
		
		private var requestObj:Object;
		 
		public function ImgSellCommand()	{
			//TODO: implement function
		}

		public function execute(event:CairngormEvent):void	{
			
			if(ModelLocator.XML_SERIALIZE_TAG == "XML")
			{
				var str:String = new String("action=sell");
				model._socket.writeUTFBytes(str);
			}
			else if(ModelLocator.XML_SERIALIZE_TAG == "SERIALIZE")
			{
	            requestObj = {event:"sell"};
	            model._socket.writeObject(requestObj);
	            model._socket.flush();
			}
		}
	}
}