// (C) Uri Wilensky. https://github.com/NetLogo/NW-Extension

package org.nlogo.extensions.nw.prim.jung

import org.nlogo.api
import org.nlogo.api.Syntax._
import org.nlogo.agent
import org.nlogo.extensions.nw.NetworkExtensionUtil.TurtleCreatingCommand
import org.nlogo.extensions.nw.jung.io.GraphMLExport
import org.nlogo.extensions.nw.jung.io.GraphMLImport
import org.nlogo.extensions.nw.GraphContextProvider

class SaveGraphML(gcp: GraphContextProvider)
  extends api.DefaultCommand {
  override def getSyntax = commandSyntax(Array(StringType))
  override def perform(args: Array[api.Argument], context: api.Context) {
    val fm = context.asInstanceOf[org.nlogo.nvm.ExtensionContext].workspace.fileManager
    GraphMLExport.save(gcp.getGraphContext(context.getAgent.world),
      fm.attachPrefix(args(0).getString))
  }
}

class LoadGraphML extends TurtleCreatingCommand {
  override def getSyntax = commandSyntax(Array(StringType, CommandBlockType | OptionalType))
  def createTurtles(args: Array[api.Argument], context: api.Context) = {
    val fm = context.asInstanceOf[org.nlogo.nvm.ExtensionContext].workspace.fileManager
    GraphMLImport.load(
      fileName = fm.attachPrefix(args(0).getString),
      world = context.getAgent.world.asInstanceOf[agent.World],
      rng = context.getRNG)
  }
}
