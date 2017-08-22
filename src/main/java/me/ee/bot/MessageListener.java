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
    if (content.equals("!ping")) {
      MessageChannel channel = event.getChannel();
      channel.sendMessage(":dog:").queue();
    }
  }
}
