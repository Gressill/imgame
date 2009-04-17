package net.imggame.command {
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import mx.controls.Alert;
	
	import net.imggame.model.ModelLocator;
	import net.imggame.model.ValueLocator;

	public class ImgStartCommand implements ICommand {
		
		public var model:ModelLocator = ModelLocator.getInstance();
		public var value:ValueLocator = ValueLocator.getInstance();
		private var requestObj:Object;
		 
		public function ImgStartCommand()	{
			//TODO: implement function
		}
		

		public function execute(event:CairngormEvent):void	{
			
			//set level.
			var level:Number = Math.pow(2,model.gameParam.m)/(model.gameParam.n as Number);
			Alert.show(level.toString());
			if(0<level<=0.04){
				value.level = "Easy";
			}else if(0.04<level<=0.08){
				value.level = "Apprentice";
			}else if(0.08<level<=0.17){
				value.level = "Trader";
			}else if(0.17<level<=0.67){
				value.level = "Proffessional";
			}else{
				value.level = "Guru";
			}
            
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