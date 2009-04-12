package net.imggame.control {
	import com.adobe.cairngorm.control.FrontController;
	import net.imggame.command.*;
	import net.imggame.event.*;

	public class ImgController extends FrontController {
		public function ImgController() {
			super();
			this.initialize();
		}
		
		private function initialize(): void {
			
			this.addCommand(ImgBuyEvent.BUY,ImgBuyCommand);
			this.addCommand(ImgSellEvent.SELL,ImgSellCommand);
			this.addCommand(ImgStartEvent.START,ImgStartCommand);
			this.addCommand(ImgCloseEvent.CLOSE,ImgCloseCommand);
			
		}
	}
}