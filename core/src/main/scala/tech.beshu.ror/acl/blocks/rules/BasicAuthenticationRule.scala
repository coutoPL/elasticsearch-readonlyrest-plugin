package tech.beshu.ror.acl.blocks.rules

import com.typesafe.scalalogging.StrictLogging
import monix.eval.Task
import tech.beshu.ror.acl.blocks.rules.BasicAuthenticationRule.Settings
import tech.beshu.ror.acl.blocks.rules.Rule.AuthenticationRule
import tech.beshu.ror.acl.requestcontext.RequestContext
import tech.beshu.ror.commons.domain.LoggedUser

import tech.beshu.ror.utils.BasicAuthUtils
import tech.beshu.ror.utils.BasicAuthUtils.BasicAuth

import scala.compat.java8.OptionConverters._
import scala.collection.JavaConverters._

abstract class BasicAuthenticationRule(settings: Settings)
  extends AuthenticationRule
    with StrictLogging {

  protected def authenticate(configuredAuthKey: String, basicAuth: BasicAuth): Boolean

  override def `match`(context: RequestContext): Task[Boolean] = Task.now {
    BasicAuthUtils
      .getBasicAuthFromHeaders(context.getHeaders.asJava).asScala
      .map { credentials =>
        logger.debug(s"Attempting Login as: ${credentials.getUserName} rc: $context")
        val authenticationResult = authenticate(settings.authKey, credentials)
        if (authenticationResult) {
          context.setLoggedInUser(new LoggedUser(credentials.getUserName))
        }
        authenticationResult
      }
      .getOrElse {
        logger.debug("No basic auth")
        false
      }
  }
}

object BasicAuthenticationRule {

  final case class Settings(authKey: String)

}