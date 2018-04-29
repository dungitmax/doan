package com.example.tandung_pc.monngonduongpho.Model.ModelFindTheWay;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();

    void onDirectionFinderSuccess(List<Route> route);
}
