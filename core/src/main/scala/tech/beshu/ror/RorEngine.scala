package tech.beshu.ror

import monix.eval.Task
import tech.beshu.ror.acl.factory.AsyncHttpClientsFactory
import tech.beshu.ror.acl.logging.AuditSink
import tech.beshu.ror.acl.{Acl, AclStaticContext}

object RorEngine {

  def start(auditSink: AuditSink): Task[Engine] = ???
}

final class Engine(val acl: Acl, val context: AclStaticContext, httpClientsFactory: AsyncHttpClientsFactory) {
  def shutdown(): Unit = {
    httpClientsFactory.shutdown()
  }
}


