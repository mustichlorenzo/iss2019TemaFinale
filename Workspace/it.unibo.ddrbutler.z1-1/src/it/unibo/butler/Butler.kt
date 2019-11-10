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
		var positive=false; var DishCarrello=0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[BUTLER]: Started...")
					}
					 transition( edgeName="goto",targetState="waitPrepare", cond=doswitch() )
				}	 
				state("waitPrepare") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in waitPrepare")
					}
					 transition(edgeName="t02",targetState="calcPathToPantryA1",cond=whenDispatch("prepare"))
				}	 
				state("calcPathToPantryA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToPantryA1")
					}
					 transition( edgeName="goto",targetState="execPathToPantryA1", cond=doswitch() )
				}	 
				state("execPathToPantryA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToPantryA1")
						delay(1000) 
						forward("completedExecToPantryA1", "completedExecToPantryA1" ,"butler" ) 
					}
					 transition(edgeName="t03",targetState="suspendedExecToPantryA1",cond=whenDispatch("stop"))
					transition(edgeName="t04",targetState="prepTakingDishA1",cond=whenDispatch("completedExecToPantryA1"))
				}	 
				state("suspendedExecToPantryA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToPantryA1")
					}
					 transition(edgeName="t05",targetState="suspendedExecToPantryA1",cond=whenDispatch("stop"))
				}	 
				state("prepTakingDishA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepTakingDishA1")
						DishCarrello=5
						forward("pantryTakeDish", "takeDish(5)" ,"pantry" ) 
					}
					 transition( edgeName="goto",targetState="calcPathToTableA1", cond=doswitch() )
				}	 
				state("calcPathToTableA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToTableA1")
					}
					 transition( edgeName="goto",targetState="execPathToTableA1", cond=doswitch() )
				}	 
				state("execPathToTableA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToTableA1")
						delay(1000) 
						forward("completedExecToTableA1", "completedExecToTableA1" ,"butler" ) 
					}
					 transition(edgeName="t06",targetState="suspendedExecToTableA1",cond=whenDispatch("stop"))
					transition(edgeName="t07",targetState="prepPuttingDishA1",cond=whenDispatch("completedExecToTableA1"))
				}	 
				state("suspendedExecToTableA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToTableA1")
					}
					 transition(edgeName="t08",targetState="suspendedExecToTableA1",cond=whenDispatch("stop"))
				}	 
				state("prepPuttingDishA1") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepPuttingDishA1")
						forward("putElement", "putElement(dish,CODE,$DishCarrello)" ,"table" ) 
					}
					 transition( edgeName="goto",targetState="calcPathToFridgeA2", cond=doswitch() )
				}	 
				state("calcPathToFridgeA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToFridgeA2")
					}
					 transition( edgeName="goto",targetState="execPathToFridgeA2", cond=doswitch() )
				}	 
				state("execPathToFridgeA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToFridgeA2")
						delay(1000) 
						forward("completedExecToFridgeA2", "completedExecToFridgeA2" ,"butler" ) 
					}
					 transition(edgeName="t09",targetState="suspendedExecToFridgeA2",cond=whenDispatch("stop"))
					transition(edgeName="t010",targetState="prepTakingFoodA2",cond=whenDispatch("completedExecToFridgeA2"))
				}	 
				state("suspendedExecToFridgeA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToFridgeA2")
					}
					 transition(edgeName="t011",targetState="suspendedExecToFridgeA2",cond=whenDispatch("stop"))
				}	 
				state("prepTakingFoodA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepTakingFoodA2")
					}
					 transition( edgeName="goto",targetState="calcPathToTableA2", cond=doswitch() )
				}	 
				state("calcPathToTableA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToTableA2")
					}
					 transition( edgeName="goto",targetState="execPathToTableA2", cond=doswitch() )
				}	 
				state("execPathToTableA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToTableA2")
						delay(1000) 
						forward("completedExecToTableA2", "completedExecToTableA2" ,"butler" ) 
					}
					 transition(edgeName="t012",targetState="suspendedExecToTableA2",cond=whenDispatch("stop"))
					transition(edgeName="t013",targetState="prepPuttingFoodA2",cond=whenDispatch("completedExecToTableA2"))
				}	 
				state("suspendedExecToTableA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToTableA2")
					}
					 transition(edgeName="t014",targetState="suspendedExecToTableA2",cond=whenDispatch("stop"))
				}	 
				state("prepPuttingFoodA2") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepPuttingFoodA2")
					}
					 transition( edgeName="goto",targetState="calcPathToRHPrep", cond=doswitch() )
				}	 
				state("calcPathToRHPrep") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToRHPrep")
					}
					 transition( edgeName="goto",targetState="execPathToRHPrep", cond=doswitch() )
				}	 
				state("execPathToRHPrep") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToRHPrep")
						delay(1000) 
						forward("completedExecToRHPrep", "completedExecToRHPrep" ,"butler" ) 
						forward("prepareCompleted", "prepareCompleted" ,"maitre" ) 
					}
					 transition(edgeName="t015",targetState="suspendedExecToRHPrep",cond=whenDispatch("stop"))
					transition(edgeName="t016",targetState="waitAC",cond=whenDispatch("completedExecToRHPrep"))
				}	 
				state("suspendedExecToRHPrep") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToRHPrep")
					}
					 transition(edgeName="t017",targetState="suspendedExecToRHPrep",cond=whenDispatch("stop"))
				}	 
				state("waitAC") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in waitAC")
					}
					 transition(edgeName="t018",targetState="makeQuery",cond=whenDispatch("addFood"))
					transition(edgeName="t019",targetState="calcPathToTableA4",cond=whenDispatch("clear"))
				}	 
				state("makeQuery") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in makeQuery")
						if( checkMsgContent( Term.createTerm("addFood(FOODCODE,QNT)"), Term.createTerm("addFood(FOODCODE,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("query", "query(${payloadArg(0)},${payloadArg(1)})" ,"fridge" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitAnswer", cond=doswitch() )
				}	 
				state("waitAnswer") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in waitAnswer")
					}
					 transition(edgeName="t020",targetState="checkAnswer",cond=whenDispatch("answer"))
				}	 
				state("checkAnswer") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in checkAnswer")
						if( checkMsgContent( Term.createTerm("answer(FOODCODE,ANS)"), Term.createTerm("answer(FOODCODE,ANS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								positive=payloadArg(1).equals("yes")
						}
					}
					 transition( edgeName="goto",targetState="calcPathToFridgeA7", cond=doswitchGuarded({positive}) )
					transition( edgeName="goto",targetState="calcPathToRHAdd", cond=doswitchGuarded({! positive}) )
				}	 
				state("calcPathToFridgeA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToFridgeA7")
					}
					 transition( edgeName="goto",targetState="execPathToFridgeA7", cond=doswitch() )
				}	 
				state("execPathToFridgeA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToFridgeA7")
						delay(1000) 
						forward("completedExecToFridgeA7", "completedExecToFridgeA7" ,"butler" ) 
					}
					 transition(edgeName="t021",targetState="suspendedExecToFridgeA7",cond=whenDispatch("stop"))
					transition(edgeName="t022",targetState="prepTakingFoodA7",cond=whenDispatch("completedExecToFridgeA7"))
				}	 
				state("suspendedExecToFridgeA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToFridgeA7")
					}
					 transition(edgeName="t023",targetState="suspendedExecToFridgeA7",cond=whenDispatch("stop"))
				}	 
				state("prepTakingFoodA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepTakingFoodA7")
					}
					 transition( edgeName="goto",targetState="calcPathToTableA7", cond=doswitch() )
				}	 
				state("calcPathToTableA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToTableA7")
					}
					 transition( edgeName="goto",targetState="execPathToTableA7", cond=doswitch() )
				}	 
				state("execPathToTableA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToTableA7")
						delay(1000) 
						forward("completedExecToTableA7", "completedExecToTableA7" ,"butler" ) 
					}
					 transition(edgeName="t024",targetState="suspendedExecToTableA7",cond=whenDispatch("stop"))
					transition(edgeName="t025",targetState="prepPuttingFoodA7",cond=whenDispatch("completedExecToTableA7"))
				}	 
				state("suspendedExecToTableA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToTableA7")
					}
					 transition(edgeName="t026",targetState="suspendedExecToTableA7",cond=whenDispatch("stop"))
				}	 
				state("prepPuttingFoodA7") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepPuttingFoodA7")
					}
					 transition( edgeName="goto",targetState="calcPathToRHAdd", cond=doswitch() )
				}	 
				state("calcPathToRHAdd") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToRHAdd")
					}
					 transition( edgeName="goto",targetState="execPathToRHAdd", cond=doswitch() )
				}	 
				state("execPathToRHAdd") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToRHAdd")
						delay(1000) 
						forward("completedExecToRHAdd", "completedExecToRHAdd" ,"butler" ) 
						forward("addFoodCompleted", "addFoodCompleted" ,"maitre" ) 
					}
					 transition(edgeName="t027",targetState="suspendedExecToRHAdd",cond=whenDispatch("stop"))
					transition(edgeName="t028",targetState="waitAC",cond=whenDispatch("completedExecToRHAdd"))
				}	 
				state("suspendedExecToRHAdd") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToRHAdd")
					}
					 transition(edgeName="t029",targetState="suspendedExecToRHAdd",cond=whenDispatch("stop"))
				}	 
				state("calcPathToTableA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToTableA4")
					}
					 transition( edgeName="goto",targetState="execPathToTableA4", cond=doswitch() )
				}	 
				state("execPathToTableA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToTableA4")
						delay(1000) 
						forward("completedExecToTableA4", "completedExecToTableA4" ,"butler" ) 
					}
					 transition(edgeName="t030",targetState="suspendedExecToTableA4",cond=whenDispatch("stop"))
					transition(edgeName="t031",targetState="prepTakingFoodA4",cond=whenDispatch("completedExecToTableA4"))
				}	 
				state("suspendedExecToTableA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToTableA4")
					}
					 transition(edgeName="t032",targetState="suspendedExecToTableA4",cond=whenDispatch("stop"))
				}	 
				state("prepTakingFoodA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepTakingFoodA4")
					}
					 transition( edgeName="goto",targetState="calcPathToFridgeA4", cond=doswitch() )
				}	 
				state("calcPathToFridgeA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToFridgeA4")
					}
					 transition( edgeName="goto",targetState="execPathToFridgeA4", cond=doswitch() )
				}	 
				state("execPathToFridgeA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToFridgeA4")
						delay(1000) 
						forward("completedExecToFridgeA4", "completedExecToFridgeA4" ,"butler" ) 
					}
					 transition(edgeName="t033",targetState="suspendedExecToFridgeA4",cond=whenDispatch("stop"))
					transition(edgeName="t034",targetState="prepPuttingFoodA4",cond=whenDispatch("completedExecToFridgeA4"))
				}	 
				state("suspendedExecToFridgeA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToFridgeA4")
					}
					 transition(edgeName="t035",targetState="suspendedExecToFridgeA4",cond=whenDispatch("stop"))
				}	 
				state("prepPuttingFoodA4") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepPuttingFoodA4")
					}
					 transition( edgeName="goto",targetState="calcPathToTableA5", cond=doswitch() )
				}	 
				state("calcPathToTableA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToTableA5")
					}
					 transition( edgeName="goto",targetState="execPathToTableA5", cond=doswitch() )
				}	 
				state("execPathToTableA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToTableA5")
						delay(1000) 
						forward("completedExecToTableA5", "completedExecToTableA5" ,"butler" ) 
					}
					 transition(edgeName="t036",targetState="suspendedExecToTableA5",cond=whenDispatch("stop"))
					transition(edgeName="t037",targetState="prepTakingDishA5",cond=whenDispatch("completedExecToTableA5"))
				}	 
				state("suspendedExecToTableA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToTableA4")
					}
					 transition(edgeName="t038",targetState="suspendedExecToTableA5",cond=whenDispatch("stop"))
				}	 
				state("prepTakingDishA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepTakingDishA5")
					}
					 transition( edgeName="goto",targetState="calcPathToDishawasherA5", cond=doswitch() )
				}	 
				state("calcPathToDishawasherA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToDishawasherA5")
					}
					 transition( edgeName="goto",targetState="execPathToDishawasherA5", cond=doswitch() )
				}	 
				state("execPathToDishawasherA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToDishawasherA5")
						delay(1000) 
						forward("completedExecToDishwasherA5", "completedExecToDishwasherA5" ,"butler" ) 
					}
					 transition(edgeName="t039",targetState="suspendedExecToDishwasherA5",cond=whenDispatch("stop"))
					transition(edgeName="t040",targetState="prepPuttingDishA5",cond=whenDispatch("completedExecToDishwasherA5"))
				}	 
				state("suspendedExecToDishwasherA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToDishwasherA5")
					}
					 transition(edgeName="t041",targetState="suspendedExecToDishwasherA5",cond=whenDispatch("stop"))
				}	 
				state("prepPuttingDishA5") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in prepPuttingDishA5")
					}
					 transition( edgeName="goto",targetState="calcPathToRHClear", cond=doswitch() )
				}	 
				state("calcPathToRHClear") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in calcPathToRHClear")
					}
					 transition( edgeName="goto",targetState="execPathToRHClear", cond=doswitch() )
				}	 
				state("execPathToRHClear") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in execPathToRHClear")
						delay(1000) 
						forward("completedExecToRHClear", "completedExecToRHClear" ,"butler" ) 
						forward("clearCompleted", "clearCompleted" ,"maitre" ) 
					}
					 transition(edgeName="t042",targetState="suspendedExecToRHClar",cond=whenDispatch("stop"))
					transition(edgeName="t043",targetState="waitPrepare",cond=whenDispatch("completedExecToRHClear"))
				}	 
				state("suspendedExecToRHClar") { //this:State
					action { //it:State
						println("[BUTLER]: Sono in suspendedExecToRHPrep")
					}
					 transition(edgeName="t044",targetState="suspendedExecToRHClar",cond=whenDispatch("stop"))
				}	 
			}
		}
}
