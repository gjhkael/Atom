package com.ctrip.atom
package snippet

import java.text.SimpleDateFormat
import java.util.Date

import com.ctrip.atom.lib._
import net.liftweb.common._
import net.liftweb.util.Helpers._

class HelloWorld {
    lazy val date: Box[Date] = DependencyFactory.inject[Date]
    // inject the date
    val sdf = new SimpleDateFormat("HH:mm:ss")

    // replace the contents of the element with id "time" with the date
    def howdy = "#time *" #> date.map(sdf.format)
    /*
     lazy val date: Date = DependencyFactory.time.vend // create the date via factory
     def howdy = "#time *" #> date.toString
     */
}

