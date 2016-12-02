package com.ctrip.atom

import org.scalatest.{Outcome, FunSuite}

/**
 * Created by huang_xw on 2016/4/14.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[atom] abstract class AtomFunSuite extends FunSuite with Logging {
    /**
     * Log the suite name and the test name before and after each test.
     *
     * Subclasses should never override this method. If they wish to run
     * custom code before and after each test, they should mix in the
     * {{org.scalatest.BeforeAndAfter}} trait instead.
     */
    final protected override def withFixture(test: NoArgTest): Outcome = {
        val testName = test.text
        val suiteName = this.getClass.getName
        val shortSuiteName = suiteName.replaceAll("com.ctrip.atom", "c.c.a")
        try {
            logInfo(s"===== TEST OUTPUT FOR $shortSuiteName:'$testName' =====\n")
            test()
        } finally {
            logInfo(s"===== FINISHED $shortSuiteName: '$testName' =====\n\n")
        }
    }
}
