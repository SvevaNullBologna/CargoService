package main.kotlin

import it.unibo.ctx_sonarservice.main as sonarMain
import it.unibo.ctx_productservice.main as productMain
import it.unibo.ctx_cargoservice.main as cargoMain
import it.unibo.ctx_companysim.main as companyMain
import it.unibo.ctx_basicrobot.main as basicRobotMain

fun main() {
    Thread { sonarMain() }.start()
    Thread { productMain() }.start()
    Thread { cargoMain() }.start()
    Thread { companyMain() }.start()
    Thread { basicRobotMain() }.start()
}
