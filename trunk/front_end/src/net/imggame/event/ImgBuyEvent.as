package net.imggame.event {
	import com.adobe.cairngorm.control.CairngormEvent;
	import flash.events.Event;

	public class ImgBuyEvent extends CairngormEvent {
		
		public static const BUY:String = "ImgBuyAction";
		
		public function ImgBuyEvent() {
			super(BUY);
		}
		
		override public function clone():Event {
			return new ImgBuyEvent();
		}
		
	}
}