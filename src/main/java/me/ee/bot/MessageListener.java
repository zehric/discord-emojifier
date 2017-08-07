package me.ee.bot;

import me.ee.emojify.Emojify;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
  private static final String DEFAULT_TEXT_EMOJI = ":bird:";
  private static final String DEFAULT_BACK_EMOJI = ":dog:";
  private static final String DEFAULT_BORDER_EMOJI = ":cat:";

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    String content = event.getMessage().getRawContent();
    if (!content.startsWith("!")) return;
    String[] args = content.split(" ");
    if (args.length > 1 && args[0].toLowerCase().equals("!e")) {
      String text = args[1];
      String textEmoji = DEFAULT_TEXT_EMOJI;
      String backEmoji = DEFAULT_BACK_EMOJI;
      String borderEmoji = DEFAULT_BORDER_EMOJI;
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
