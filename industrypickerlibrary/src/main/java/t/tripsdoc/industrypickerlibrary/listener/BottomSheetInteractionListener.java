package t.tripsdoc.industrypickerlibrary.listener;

import android.view.View;

public interface BottomSheetInteractionListener {
    void initiateUi(View view);

    void setSearchEditText();

    void setupRecyclerView(View view);
}
