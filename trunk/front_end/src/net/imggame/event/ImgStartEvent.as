package net.imggame.event {
	import com.adobe.cairngorm.control.CairngormEvent;
	import flash.events.Event;

	public class ImgStartEvent extends CairngormEvent {
		
		public static const START:String = "ImgStartAction";
		
		public function ImgStartEvent() {
			super(START);
		}
		
		override public function clone():Event {
			return new ImgStartEvent();
		}
		
	}
}