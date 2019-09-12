package itunibo.planner

import aima.core.agent.Action
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import itunibo.planner.model.RobotState.Direction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import itunibo.planner.model.RoomMap
import itunibo.planner.model.Box

object moveUtils{
    private val actions : List<Action>? = null
    private var existPlan = false

	private var mapDims   : Pair<Int,Int> = Pair(0,0)
	private var curPos    : Pair<Int,Int> = Pair(0,0)
	private var curGoal   : Pair<Int,Int> = Pair(0,0)
	private var direction = "downDir"
	private val PauseTime = 250
	
	private var MaxX        = 0
	private var MaxY        = 0
	
	
    private fun storeMovesInActor( actor : ActorBasic, actions : List<Action>?  ) {
        if( actions == null ) return
        val iter = actions.iterator()
        while (iter.hasNext()) {
            val a = iter.next()
            actor.solve("assert( move($a) )")
        }
    }
	fun loadRoomMap( actor : ActorBasic,  fname : String ){
		val dims = plannerUtil.loadRoomMap( fname )
		memoMapDims( actor, dims )
 	}
	fun saveMap( actor : ActorBasic, fname : String) {
		val dims = plannerUtil.saveMap( fname )
		memoMapDims( actor, dims )
 	}	
	fun memoMapDims( actor : ActorBasic, dims : Pair<Int,Int> ){
		mapDims = dims
		MaxX    = dims.first
		MaxY    = dims.second
		actor.solve("retract( mapdims(_,_) )")		//remove old data
		actor.solve("assert(  mapdims( ${dims.first},${dims.second} ) )")				
	}
	
 	fun getMapDimX( ) 	: Int{ return mapDims.first }
	fun getMapDimY( ) 	: Int{ return mapDims.second }
 	fun getPosX(actor : ActorBasic)    	  : Int{ setPosition(actor); return curPos.first  } 
	fun getPosY(actor : ActorBasic)    	  : Int{ setPosition(actor); return curPos.second }
	fun getDirection(actor : ActorBasic)  : String{ setPosition(actor);return direction.toString() }
	fun mapIsEmpty() : Boolean{return (getMapDimX( )==0 &&  getMapDimY( )==0 ) }
	
	
	fun showCurrentRobotState(){
		println("===================================================")
		plannerUtil.showMap()
		direction = plannerUtil.getDirection()
		println("RobotPos=(${curPos.first}, ${curPos.second}) in map($MaxX,$MaxY) direction=$direction")
		println("===================================================")
	}
 	fun setObstacleOnCurrentDirection( actor : ActorBasic ){
		doPlannedMove(actor, direction )
	}
	
	fun setDuration( actor : ActorBasic ){
		val time = plannerUtil.getDuration()
		actor.solve("retract( wduration(_) )")		//remove old data
		actor.solve("assert( wduration($time) )")
 	}
	
	fun setDirection( actor : ActorBasic )  {
		direction = plannerUtil.getDirection()
		//println("moveUtils direction=$direction")
		actor.solve("retract( direction(_) )")		//remove old data
		actor.solve("assert( direction($direction) )")
 	}
	
	
	fun setGoal( actor : ActorBasic, x: String, y: String) {
		val xv = Integer.parseInt(x)
		val yv = Integer.parseInt(y)
		plannerUtil.setGoal( xv,yv )
		curGoal=Pair(xv,yv)
		actor.solve("retract( curGoal(_,_) )")		//remove old data
		actor.solve("assert( curGoal($x,$y) )")
	}	

	
	fun doPlan(actor : ActorBasic ){
		val plan = plannerUtil.doPlan(  )
		existPlan = plan != null
		if( existPlan ) storeMovesInActor(actor,plan) 
	}
	
	fun existPlan() : Boolean{ return existPlan }

	fun doPlannedMove(actor : ActorBasic, move: String){
		plannerUtil.doMove( move )
		setPosition(actor)
		//setDirection( actor )
	}
	
	fun setPosition(actor : ActorBasic){
		direction     = plannerUtil.getDirection()
		val posx      = plannerUtil.getPosX()
		val posy      = plannerUtil.getPosY()
		curPos        = Pair( posx,posy )
		
		//println("setPosition curPos=($posx,$posy,$direction)")
		actor.solve("retract( curPos(_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy) )")			
		actor.solve("retract( curPos(_,_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy,$direction) )")			
	}
	
	suspend fun rotate(actor:ActorBasic,move:String,pauseTime:Int=PauseTime){
		when( move ){
			"a" -> rotateLeft(actor )
			"d" -> rotateRight(actor)
			"l" -> rotateLeft90( actor )
		    "r" -> rotateRight90( actor )
			else -> println("rotate $move unknown")
		}
  	}
 	suspend fun rotateRight(actor : ActorBasic){
 		actor.forward("mindCmd", "mindCmd(d)", "robotmind")
 		doPlannedMove(actor, "d" )	    //update map
		//delay( pauseTime.toLong() )
	}
 	suspend fun rotateRight90(actor : ActorBasic ){
 		actor.forward("modelChange", "modelChange(robot,r)", "resourcemodel")
		delay( 800 )
 		doPlannedMove(actor, "r" )	    //update map
 	}
 	suspend fun rotateRight90tuning(actor : ActorBasic ){
 		//actor.forward("mindCmd", "mindCmd(d)", "robotmind")
		println("TUNING TO RIGHT.... ")
 		//readLine()
 		doPlannedMove(actor, "d" )	    //update map
 	}
	suspend fun rotateLeft(actor : ActorBasic){
		actor.forward("mindCmd", "mindCmd(a)", "robotmind")
 		doPlannedMove(actor, "a" )	    //update map	
		//delay( pauseTime.toLong() )
	}
	suspend fun rotateLeft90( actor : ActorBasic ){
		actor.forward("modelChange", "modelChange(robot,l)", "resourcemodel")
		delay( 800 )
 		doPlannedMove(actor, "l" )	    //update map	
 	}
	suspend fun rotateLeft90tuning( actor : ActorBasic ){
		//actor.forward("mindCmd", "mindCmd(a)", "robotmind")
		println("TUNING TO LEFT.... ")
		//readLine()
		//delay( 1000 )
 		doPlannedMove(actor, "a" )	    //update map
	}
 	suspend fun moveAhead(actor:ActorBasic, dest:String ="robotmind"){
		//println("moveUtils moveAhead stepTime=$stepTime")
		actor.forward("mindCmd", "mindCmd(w)", dest)
		//delay( stepTime.toLong() )
		//actor.forward("modelChange", "modelChange(robot,h)", dest)
		doPlannedMove(actor, "w" )	//update map	
		//delay( pauseTime.toLong() )
	} 
	suspend fun attemptTomoveAhead(actor:ActorBasic,stepTime:Int, dest:String ="onestepahead"){
 		//println("moveUtils attemptTomoveAhead stepTime=$stepTime")
		actor.forward("onestep", "onestep(${stepTime})", dest)
   	}
	fun updateMapAfterAheadOk(actor : ActorBasic ){
		doPlannedMove(actor  , "w")
	}
	suspend fun backToCompensate(actor : ActorBasic){
		//println("moveUtils backToCompensate stepTime=$stepTime")
		actor.forward("mindCmd", "mindCmd(s)", "robotmind")
		//delay( stepTime.toLong() )
		//actor.forward("modelChange", "modelChange(robot,h)", "resourcemodel")
		//delay( pauseTime.toLong() )
		//doPlannedMove(actor, "s" )
   	}
	
	//CUSTOM
	fun setTable(startX : Int,startY : Int,endX : Int,endY : Int,lengthX : Int,lengthY : Int){
		var startMaxX = Math.min(startX,endX)
		var startMaxY = Math.min(startY,endY)
		var endMaxX =	Math.max(startX,endX)
		var endMaxY =	Math.max(startY,endY)
		
		for(i in startMaxX..endMaxX){
			for(j in startMaxY..endMaxY){
				RoomMap.getRoomMap().put(i,j,Box(true,false,false))
			}
		}
	}
	
}
