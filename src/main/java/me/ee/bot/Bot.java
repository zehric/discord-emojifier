package me.ee.bot;

import java.io.IOException;
import java.net.ServerSocket;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Bot {
  private static final String TOKEN = "MjEwOTAzNTM0MzQ0ODYzNzQ2.DGVPtQ.7vHFhTtaqGSpfnIUpjOddOXhY3o";
  public static void main(String[] args)
      throws LoginException, RateLimitedException, InterruptedException, IOException {
    JDA jda = new JDABuilder(AccountType.BOT).setToken(TOKEN).buildBlocking();
    jda.addEventListener(new MessageListener());

    ServerSocket s = new ServerSocket(Integer.parseInt(System.getenv("PORT")));
    System.out.println("Booted server.  " + Integer.parseInt(System.getenv("PORT")));
  }
}
