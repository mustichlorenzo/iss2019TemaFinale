package it.unibo

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After
import org.junit.Before
import org.junit.Test
import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class TestPositionRobot {
 	var resource : ActorBasic? = null
	var mind : ActorBasic? = null
	
	@Before
	fun systemSetUp() {
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestLogicButler starts robot mind ")
				it.unibo.ctxButler.main()
 			}
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("resourcemodelbutler")
		    mind = sysUtil.getActor("butlermind")
		    println(" %%%%%%% TestLogicButler getActors resource=${resource}")
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% TestLogicButler terminate ")
	}
 
	@Test
	fun moveTest() {
		println(" %%%%%%% TestLogicButler  logic test ")
		delay(100)
		checkInit(0)
		checkRH(1000)
 	}
	
	fun checkInit(time:Long ){
		solveCheckGoal( resource!!,  "position( butler, [0,0] )" )
	}
	
	fun checkRH(time:Long ){
		GlobalScope.launch{
			mind!!.forward("prepare","prepare","butlermind")
		}
		delay(time)
		solveCheckGoal( resource!!,  "position( butler, [0,0] )" )
	}
	
	fun checkAddFood(time:Long ){
		GlobalScope.launch{
			mind!!.forward("addFood","addFood(cibo1,qnt1)","butlermind")
		}
		solveCheckGoal( resource!!,  "state( butler, addFood )" )
	}
	
	fun checkClear(time:Long ){
		GlobalScope.launch{
			mind!!.forward("clear","clear","butlermind")
		}
		solveCheckGoal( resource!!,  "state( butler, clear )" )
	}

//----------------------------------------

 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue("", result == "success" )
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}
}

