package org.gwtf

import groovy.lang.Closure;
import groovy.lang.Script;

public class ClosureScript extends Script {

	Closure closure
	def run() {
		closure.resolveStrategy = Closure.DELEGATE_FIRST
		closure.delegate = this
		closure.call()
	}
}