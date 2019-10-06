/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodelpantry

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodelpantry ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[RESOURCEMODEL PANTRY]: Started...")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('pantryModel.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t0100",targetState="handleUpdate",cond=whenDispatch("modelUpdatePantry"))
				}	 
				state("handleUpdate") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelUpdatePantry(NAME,OP,QNT)"), Term.createTerm("modelUpdatePantry(NAME,OP,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								itunibo.pantry.resourceModelSupport.updatePantryModel(myself ,payloadArg(1), payloadArg(2) )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
