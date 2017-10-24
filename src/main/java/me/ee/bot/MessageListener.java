package me.ee.bot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.Random;
import me.ee.emojify.Emojify;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
  private static final Emoji[] emojis = EmojiManager.getAll()
      .toArray(new Emoji[EmojiManager.getAll().size()]);
  private static final Random rng = new Random();

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    String content = event.getMessage().getRawContent();
    if (!content.startsWith("!")) return;
    String[] args = content.split(" ");
    if (args.length > 1 && args[0].toLowerCase().equals("!e")) {
      String text = args[1];
      String textEmoji = emojis[rng.nextInt(emojis.length)].getUnicode();
      String backEmoji = emojis[rng.nextInt(emojis.length)].getUnicode();
      String borderEmoji = emojis[rng.nextInt(emojis.length)].getUnicode();
      if (args.length > 2) {
        textEmoji = args[2];
      }
      if (args.length > 3) {
        backEmoji = args[3];
      }
      if (args.length > 4) {
        borderEmoji = args[4];
      }
      Emojify emojify = new Emojify(text, textEmoji, backEmoji, borderEmoji);
      String emojiString = emojify.emojify();
      if (emojiString.equals("")) return;
      MessageChannel channel = event.getChannel();
      channel.sendMessage(emojify.emojify()).queue();
    }
    if (args.length == 2 && args[0].toLowerCase().equals("!r")) {
      String diceSize = args[1];
      int n = 1;
      if (args[1].contains("d")) {
        String[] roll1 = args[1].split("d");
        diceSize = roll1[1];
        if (!roll1[0].equals("")) {
          n = Integer.parseInt(roll1[0]);
        }
      }
      int adder = 0;
      if (diceSize.contains("+")) {
        String[] roll2 = diceSize.split("\\+");
        diceSize = roll2[0];
        adder = Integer.parseInt(roll2[1]);
      }
      if (diceSize.contains("-")) {
        String[] roll2 = diceSize.split("-");
        diceSize = roll2[0];
        adder = -Integer.parseInt(roll2[1]);
      }
      int size = Integer.parseInt(diceSize);
      if (n > 0 && size > 0 && n < 100) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int total = 0;
        for (int i = 0; i < n; i++) {
          int r = rng.nextInt(size) + 1;
          total += r;
          sb.append(r);
          sb.append(' ');
        }
        sb.append(']');
        if (adder > 0) {
          sb.append(" + ");
          sb.append(adder);
          total += adder;
        }
        if (adder < 0) {
          sb.append(" - ");
          sb.append(-adder);
          total += adder;
        }
        sb.append('\n');
        sb.append("**");
        sb.append(total);
        sb.append("**");
        MessageChannel channel = event.getChannel();
        channel.sendMessage(sb.toString()).queue();
      }
    }
  }
}
