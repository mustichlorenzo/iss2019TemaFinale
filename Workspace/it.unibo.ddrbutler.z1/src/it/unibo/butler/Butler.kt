/* Generated by AN DISI Unibo */ 
package it.unibo.butler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[BUTLER]: Started...")
					}
					 transition( edgeName="goto",targetState="westWall", cond=doswitch() )
				}	 
				state("westWall") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in westWall")
					}
					 transition(edgeName="t02",targetState="turnLeft1",cond=whenEvent("obstacle"))
					transition(edgeName="t03",targetState="westWall",cond=whenDispatch("westWallCompleted"))
				}	 
				state("turnLeft1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in turnLeft1")
					}
					 transition( edgeName="goto",targetState="southWall", cond=doswitch() )
				}	 
				state("southWall") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in southWall")
					}
				}	 
			}
		}
}
