package net.imggame.event {
	import com.adobe.cairngorm.control.CairngormEvent;
	import flash.events.Event;

	public class ImgCloseEvent extends CairngormEvent {
		
		public static const CLOSE:String = "ImgCloseAction";
		
		public function ImgCloseEvent() {
			super(CLOSE);
		}
		
		override public function clone():Event {
			return new ImgCloseEvent();
		}
		
	}
}