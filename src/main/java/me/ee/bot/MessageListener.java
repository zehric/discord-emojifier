package me.ee.bot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.Random;
import me.ee.emojify.Emojify;
import me.ee.roll.Dice;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
  private static final Emoji[] emojis = EmojiManager.getAll()
      .toArray(new Emoji[EmojiManager.getAll().size()]);
  private static final Random rng = new Random();
  private static final Dice d = new Dice(rng);

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

    args = content.split(" ", 2);
    if (args.length == 2 && args[0].toLowerCase().equals("!r")) {
      String diceSize = args[1];
      int n = 1;
      if (args[1].contains("d")) {
        String[] roll1 = args[1].split("d");
        roll1[0] = roll1[0].trim();
        roll1[1] = roll1[1].trim();
        diceSize = roll1[1];
        if (!roll1[0].equals("")) {
          n = Integer.parseInt(roll1[0]);
        }
      }
      int adder = 0;
      if (diceSize.contains("+")) {
        String[] roll2 = diceSize.split("\\+");
        roll2[0] = roll2[0].trim();
        roll2[1] = roll2[1].trim();
        diceSize = roll2[0];
        adder = Integer.parseInt(roll2[1]);
      }
      if (diceSize.contains("-")) {
        String[] roll2 = diceSize.split("-");
        roll2[0] = roll2[0].trim();
        roll2[1] = roll2[1].trim();
        diceSize = roll2[0];
        adder = -Integer.parseInt(roll2[1]);
      }
      int size = Integer.parseInt(diceSize);
      if (n > 0 && size > 0 && n < 100) {
        String r = d.roll(n, size, adder, event.getAuthor().getName());
        event.getChannel().sendMessage(r).queue();
      }
    }
  }
}
