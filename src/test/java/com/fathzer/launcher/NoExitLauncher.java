package com.fathzer.launcher;

public class NoExitLauncher extends Launcher {
	private Integer exitCode;

	
	@Override
	protected void doMain(String[] args, Output defaultOutput) {
		exitCode = null;
		super.doMain(args, defaultOutput);
	}

	@Override
	protected void exit(int code) {
		exitCode = code;
	}

	public Integer getExitCode() {
		return exitCode;
	}
}
