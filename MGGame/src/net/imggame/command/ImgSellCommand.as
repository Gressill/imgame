package net.imggame.command {
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.adobe.cairngorm.commands.ICommand;

	import flash.net.*;
	import flash.utils.*;
	import net.imggame.model.ModelLocator;
	import mx.controls.Alert;
	
	public class ImgSellCommand implements ICommand {
		
		public var model:ModelLocator = ModelLocator.getInstance();
		
		public function ImgSellCommand() {
			
			//TODO: implement function
		
		}

		public function execute11(event:CairngormEvent):void
		{
			//TODO: implement function
			var random_minus:uint = Math.round(Math.random() * 10 );
			
			var last_price:uint  = model.ImgPriceData.getItemAt(model.ImgPriceData.length-1) as uint;
			//Alert.show(a.toString());
			model.ImgPriceData.addItem( last_price - random_minus );
			
		}
		
		public function sendMessage(msg:String):void //发送数据到服务器
		{
		        //新建一个ByteArray来存放数据
		        var message:ByteArray=new ByteArray();
		        //写入数据，使用writeUTFBytes以utf8格式传数据，避免中文乱码
		        message.writeUTFBytes(msg+"\n");
		        //写入socket的缓冲区
		        model.socket.writeBytes(message);
		//调用flush方法发送信息
		        model.socket.flush();
		        //清空消息框
		        //myInput.text="";
		}
		
		public function execute(event:CairngormEvent):void
		{
			//TODO: implement function
			//sendMessage("sell");
			var random_minus:uint = Math.round(Math.random());
			
			model.ImgTempData = 0;
			var i:int=new int;
			while(i<100)
			{
				var random_add:uint = Math.round(Math.random());
				if(random_add == 0)
				{
					model.ImgTempData--;
				}
				else
				{
					model.ImgTempData++;
				}
				i++;
			}
			var last_price:uint  = model.ImgPriceData.getItemAt(model.ImgPriceData.length-1) as uint;
			
			//Alert.show(model.ImgTempData.toString());
			model.ImgPriceData.addItem( last_price + model.ImgTempData );
			
		}
		
	}
}