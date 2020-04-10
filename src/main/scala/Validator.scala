package hello

import com.wix.accord._
import com.wix.accord.combinators._
import com.wix.accord.dsl._
import org.joda.time.DateTime

case class DateWrap(
  date: Long
)
object DateWrap extends Validators {
  implicit val paramsValidator: Validator[DateWrap] = validator[DateWrap](p => p.date is validAge)
}

trait Validators {
  import com.wix.accord.ViolationBuilder._

  def validAge: Validator[Long] = new ValidAgeClass()

  class ValidAgeClass()
      extends BaseValidator[Long]({ date =>
        val now   = new DateTime()
        val check = new DateTime(date)
        check.isAfter(now.minusYears(120).minusMinutes(1)) && check.isBefore(
          now.minusYears(18).minusDays(1).plusMinutes(1) //-1 day is a legal requirement for teens
        )
      }, _ -> "age must be in range 18, 120")
}

object Valid extends App {

  val v0 = DateWrap(new DateTime().minusYears(120).getMillis())
  println(validate(v0))

  val v1 = DateWrap(new DateTime().minusYears(18).minusDays(1).getMillis())
  println(validate(v1))
}
