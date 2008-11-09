package net.imggame.factory {
	import flash.events.MouseEvent;
	
	import net.imggame.model.ModelLocator;
	
	public class WorkflowChange {
		
		public static var model:ModelLocator = ModelLocator.getInstance();;
		
		public function WorkflowChange(){
			
		}
		
		public static function goToImgGame( e:MouseEvent ): void {
			
			model.workflowState = ModelLocator.IMG_MODEL;
			
		}
		
		public static function goToOtherGame( e:MouseEvent ) :void {
			
			model.workflowState = ModelLocator.OTHER_MODEL;
			
		}
		
		public static function goBackToChoose( e:MouseEvent ): void{
			
			model.workflowState = ModelLocator.CHOOSE_MODEL;
			
		}

	}
}