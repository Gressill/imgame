package net.imggame.event {
	import com.adobe.cairngorm.control.CairngormEvent;
	import flash.events.Event;

	public class ImgSellEvent extends CairngormEvent {
		
		public static const SELL:String = "ImgSellAction";
		
		public function ImgSellEvent() {
			super(SELL);
		}
		
		override public function clone():Event {
			return new ImgSellEvent();
		}
		
	}
}