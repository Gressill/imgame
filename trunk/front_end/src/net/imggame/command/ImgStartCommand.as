package net.imggame.command {
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.net.Socket;
	
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;
	
	import net.imggame.business.Imgsocket;
	import net.imggame.model.ModelLocator;
		
	//import mx.controls.Alert;

	public class ImgStartCommand implements ICommand {
		
		public var model:ModelLocator = ModelLocator.getInstance();
		private var requestObj:Object;
		 
		public function ImgStartCommand()	{
			//TODO: implement function
		}
		

		public function execute(event:CairngormEvent):void	{
			
			if(ModelLocator.XML_SERIALIZE_TAG == "XML"){
				var str:String = new String("s="+model.gameParam.s+"&m="+model.gameParam.m+"&n="+model.gameParam.n);
			    model._socket.writeUTFBytes(str);
			}
			else if(ModelLocator.XML_SERIALIZE_TAG == "SERIALIZE"){
				requestObj = new Object();
            	requestObj = {event:"gameInit",m:model.gameParam.m,s:model.gameParam.s,n:model.gameParam.n,user:model.username};
            	model._socket.writeObject(requestObj);
			}
			
            model._socket.flush();
		}
	}
}