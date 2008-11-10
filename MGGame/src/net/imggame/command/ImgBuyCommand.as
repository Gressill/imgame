package net.imggame.command {
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.events.*;
	import flash.net.*;
	import flash.utils.*;
	import mx.controls.Alert;
	
	import net.imggame.model.ModelLocator;
	import net.imggame.view.*;
	
	[Event(name="onShowMessage")]

	public class ImgBuyCommand extends EventDispatcher implements ICommand	{
		
		public var model:ModelLocator = ModelLocator.getInstance();
		
		public function ImgBuyCommand()	{
			//TODO: implement function
		}

		public function execute11(event:CairngormEvent):void	{
			//TODO: implement function
			//Alert.show("Img Good Game");
			var random_add:uint = Math.round(Math.random() * 10 );
			
			var last_price:uint  = model.ImgPriceData.getItemAt(model.ImgPriceData.length-1) as uint;
			
			model.ImgPriceData.addItem( last_price + random_add );
			
			//Alert.show(model.ImgPriceData.toString());
			
		}
		
		public function buySendMessage():void
		{
		  	dispatchEvent(new Event("onSendMessage"));
		  	//Alert.show("buysend \n");
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
		
		public function execute(event:CairngormEvent):void	{
			//TODO: implement function
			//Alert.show("Img Good Game");
			//IMGGame(Application.application).sendMessage("buy");
			sendMessage("buy");
							
			
			var last_price:uint  = model.ImgPriceData.getItemAt(model.ImgPriceData.length-1) as uint;
			
//			var loader:URLLoader = new URLLoader( );
//			//loader.dataFormat = DataFormat.TEXT;
//			loader.dataFormat = URLLoaderDataFormat.TEXT;
//			loader.addEventListener( Event.COMPLETE, handleComplete );
//			loader.load( new URLRequest( "F:\\文档\\MyProjects\\IMGGame\\src\\net\\imggame\\file1.xml" ) );
			
			
		}
		
		private function handleComplete( event:Event ):void {
			
				try {
					// Convert the downlaoded text into an XML instance
					var example:XML = new XML( event.target.data );
					for each(var price:XML in example..price)
					{
						// At this point, example is ready to be used with E4X
						//mx.controls.Alert.show( price );
						model.ImgPriceData.addItem( price );
					}
					
				} catch ( e:TypeError ) {
					// If we get here, that means the downloaded text could
					// not be converted into an XML instance, probably because
					// it is not formatted correctly.
					trace( "Could not parse text into XML" );
					trace( e.message );
				}
				
				//humen agent
				model.ImgScore = example.turninfo.score;
				model.ImgAvgScore = example.turninfo.avgscore;
				
				model.ImgTuennum= example.turninfo.turnnum;
				
				//mg agents
				model.ImgBest = example.agents.best;
				model.ImgWorst = example.agents.worst;
				model.ImgAvg = example.agents.avg;
		}
		
	}
}