package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch

object resourceModelSupport{
	 
	fun updateRobotModel( actor: ActorBasic, content: String ){
 			actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			actor.solve(  "state( robot, STATE )" )
			val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
	}	
	fun updateSonarRobotModel( actor: ActorBasic, content: String, obstacle: Boolean ){
			var obstacleParam="clear"
			if(obstacle){
				obstacleParam="obstacle"
			}
 			actor.solve( "action( sonar,  $content, $obstacleParam )" ) //change the robot state model
			actor.solve( "state( sonar, STATE, OBSTACLE )" )
			val SonarState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateSonarRobotModel SonarState=$SonarState")	
	}	
	fun updateButlerModel( actor: ActorBasic, content: String){	
		actor.solve(  "action(butler, $content )" )
	}
	fun updatePositionRobotModel( actor: ActorBasic, x: String, y: String ){
 			actor.solve(  "action(position, $x, $y )" ) //change the robot state model
			actor.solve(  "position( robot, X,Y)" )
			val solx = actor.getCurSol("X")
			val soly = actor.getCurSol("Y")
			println("UPDATE POSITION: robot $solx $soly")
	}	
}

