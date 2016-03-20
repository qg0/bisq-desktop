package io.bitsquare.gui.main.overlays.windows;

import com.google.inject.Inject;
import io.bitsquare.app.BitsquareApp;
import io.bitsquare.common.util.Utilities;
import io.bitsquare.gui.components.HyperlinkWithIcon;
import io.bitsquare.gui.main.overlays.Overlay;
import io.bitsquare.user.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.bitsquare.gui.util.FormBuilder.addHyperlinkWithIcon;

public class TacWindow extends Overlay<TacWindow> {
    private static final Logger log = LoggerFactory.getLogger(TacWindow.class);
    private final Preferences preferences;

    @Inject
    public TacWindow(Preferences preferences) {
        this.preferences = preferences;
        type = Type.Attention;
        width = 800;
    }

    public void showIfNeeded() {
        if (!preferences.getTacAccepted() && !BitsquareApp.DEV_MODE) {
            headLine("User agreement");
            String text = "1. This software is experimental and provided \"as is\", without warranty of any kind, " +
                    "express or implied, including but not limited to the warranties of " +
                    "merchantability, fitness for a particular purpose and non-infringement.\n" +
                    "In no event shall the authors or copyright holders be liable for any claim, damages or other " +
                    "liability, whether in an action of contract, tort or otherwise, " +
                    "arising from, out of or in connection with the software or the use or other dealings in the software.\n\n" +
                    "2. The user is responsible to use the software in compliance with local laws. Don't use Bitsquare if the usage of Bitcoin is not legal in your jurisdiction.\n\n" +
                    "3. The user confirms that he has read and agreed to the rules regrading the dispute process:\n" +
                    "    - You must finalize trades within the maximum duration specified for each payment method.\n" +
                    "    - You must enter the correct reference text for your payment transfers.\n" +
                    "    - You must cooperate with the arbitrator during the arbitration process.\n" +
                    "    - You must reply within 48 hours to each arbitrator inquiry.\n" +
                    "    - Failure to follow the above requirements may result in loss of your security deposit.\n\n" +
                    "For more details and a general overview please read the full documentation about the " +
                    "arbitration system and the dispute process at:";
            message(text);
            actionButtonText("I agree");
            closeButtonText("I disagree and quit");
            onAction(() -> preferences.setTacAccepted(true));
            onClose(BitsquareApp.shutDownHandler::run);
            super.show();
        }
    }

    @Override
    protected void addMessage() {
        super.addMessage();
        String url = "https://bitsquare.io/arbitration_system.pdf";
        HyperlinkWithIcon hyperlinkWithIcon = addHyperlinkWithIcon(gridPane, ++rowIndex, url, -8);
        hyperlinkWithIcon.setOnAction(e -> Utilities.openWebPage(url));
    }

    @Override
    protected void onShow() {
        display();
    }
}