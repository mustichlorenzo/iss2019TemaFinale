/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodeldishwasher

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodeldishwasher ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[RESOURCEMODEL DISHWASHER]: Started...")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('dishwasherModel.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t082",targetState="handleUpdate",cond=whenDispatch("modelUpdateDishwasher"))
				}	 
				state("handleUpdate") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelUpdateDishwasher(NAME,OP,QNT)"), Term.createTerm("modelUpdateDishwasher(dishwasher,OP,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								itunibo.dishwasher.resourceModelSupport.updateDishwasherModel(myself ,payloadArg(1), payloadArg(2) )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
