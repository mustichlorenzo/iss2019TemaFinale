/* Generated by AN DISI Unibo */ 
package it.unibo.preparehandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Preparehandler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var isFood : Boolean = false
			  var DishesToPut = 0
		      var foodToPut = HashMap<String, String>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[PREPARE_HANDLER]: starts...")
						solve("consult('initialFood.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitingCmd", cond=doswitch() )
				}	 
				state("waitingCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						
									isFood = false
									DishesToPut = 0
						     		foodToPut = HashMap<String, String>()
						println("[PREPARE_HANDLER]: waiting for a command...")
					}
					 transition(edgeName="t075",targetState="planP",cond=whenDispatch("startPrepare"))
				}	 
				state("planP") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("goto", "goto(pantry)" ,"planner" ) 
					}
					 transition(edgeName="t076",targetState="takingDishes",cond=whenDispatch("planningCompleted"))
				}	 
				state("takingDishes") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						solve("dish(X)","") //set resVar	
						if(currentSolution.isSuccess()) { var Temp=getCurSol("X").toString()
						forward("modelChangePantry","modelChangePantry(pantry, take, $Temp)","resourcemodelpantry")
						DishesToPut = Integer.parseInt(getCurSol("X").toString())
						println("[PREPARE_HANDLER]: I'm taking ${getCurSol("X")} dishes")
						emit("updateContent", "updateContent(pantry,dish,null,$DishesToPut,take)" ) 
						 }
						else
						{ println("[PREPARE_HANDLER]: error in taking dishes")
						 }
					}
					 transition( edgeName="goto",targetState="planT", cond=doswitch() )
				}	 
				state("planT") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("goto", "goto(table)" ,"planner" ) 
					}
					 transition(edgeName="t077",targetState="puttingDishes",cond=whenDispatchGuarded("planningCompleted",{!isFood}))
					transition(edgeName="t078",targetState="puttingFood",cond=whenDispatchGuarded("planningCompleted",{isFood}))
				}	 
				state("puttingDishes") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("[PREPARE_HANDLER]: I'm putting the dishes")
						if(!isFood) isFood = true
						forward("modelChangeTable","modelChangeTable(table, dish, put, null, $DishesToPut)","resourcemodeltable")
						emit("updateContent", "updateContent(table,dish,null,$DishesToPut,put)" ) 
					}
					 transition( edgeName="goto",targetState="planF", cond=doswitch() )
				}	 
				state("planF") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("goto", "goto(fridge)" ,"planner" ) 
					}
					 transition(edgeName="t079",targetState="takingFood",cond=whenDispatch("planningCompleted"))
				}	 
				state("takingFood") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("[PREPARE_HANDLER]: i'm taking all the food")
						solve("findall(food(X,Y),food(X,Y),L)","") //set resVar	
						if(currentSolution.isSuccess()) { var foodList = getCurSol("L").toString().replace("]", "").replace("[", "").replace("food(","").replace("),",";").replace(")","").split(";")
						var FoodCode = ""; var Qnt ="" 
						for(s : String in foodList) {
										println(s)	
										FoodCode = s.split(",")[0]
										Qnt = s.split(",")[1]
						
										foodToPut.put(FoodCode, Qnt)
						var qntInteger=Integer.parseInt(Qnt)
						forward("modelChangeFridge","modelChangeFridge(fridge,take,$FoodCode,$Qnt)","resourcemodelfridge")
						emit("updateContent", "updateContent(fridge,food,$FoodCode,$Qnt,take)" ) 
						}
						 }
						else
						{ println("[PREPARE_HANDLER]: error in taking food")
						 }
					}
					 transition( edgeName="goto",targetState="planT", cond=doswitch() )
				}	 
				state("puttingFood") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("[PREPARE_HANDLER]: I'm putting all the food on the table")
						foodToPut.forEach { (K, V) -> println("[PREPARE_HANDLER]: I'm putting on the table $K, $V")
						forward("modelChangeTable","modelChangeTable(table, food, put, $K, $V)","resourcemodeltable")
						emit("updateContent", "updateContent(table,food,$K,$V,put)" ) 
						}
					}
					 transition( edgeName="goto",targetState="planRH", cond=doswitch() )
				}	 
				state("planRH") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("goto", "goto(rh)" ,"planner" ) 
					}
					 transition(edgeName="t080",targetState="endPrepare",cond=whenDispatch("planningCompleted"))
				}	 
				state("endPrepare") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("prepareCompleted", "prepareCompleted" ,"butlermind" ) 
					}
					 transition( edgeName="goto",targetState="waitingCmd", cond=doswitch() )
				}	 
			}
		}
}
