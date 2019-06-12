package com.example.lizhijiang.myapplication.lrc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lizhijiang.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LRCShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lrc_show);

        LrcView lrcView = (LrcView) findViewById(R.id.lrc_view);

        lrcView.reset();

        lrcView.onLrcLoaded(parseLrc());
    }

    private List<LrcEntry> parseLrc() {

        List<LrcEntry> entryList = fakeData();


        return entryList;
    }

    public List<LrcEntry> fakeData(){
        String aa = "备妥入|境材料。|入境时|需填写|入境登|记卡，|请务必|填写完|整所列|信息，不要|遗漏。请您|留意，根|据国际|惯例，即|使持有有|效签证，孟|移民官员|仍会要求您|出示邀请|函、返程|(或续程)" +
                "机票、酒|店预定单、在|孟居住地址、联|系方式、财务|证明等相|关材料，如有|任何怀疑，亦|可能拒绝您|入境。因此|出国前|需备妥相|关证明材|料留意孟|治安形势和|旅行安全。孟|人员流|动性大，偷、盗、抢|等案件|" +
                "时有发生。|同时，近年|来，孟极|端势力有|所抬头，2016年|曾发生针对|外国人的|恐怖袭击。故|在孟旅行时，要|提高安全意识|，警惕可疑人|员，加强安全|防护，保护人身|和财产安全。|尽量减少单" +
                "|独出行尤其|是夜间出行，|尽量避免前|往外国人集|中的场所";
        aa = "下备妥入|境材料。|入境时发生针高安全意外国人集能拒绝您移民官员治安形势|同时，近年|来，孟极|端势力有恐怖袭击。故|在孟旅行时，要|提高安全意识";

        String[] array = aa.split("\\|");
        List<LrcEntry> entryList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            LrcEntry lrcEntry = new LrcEntry(array[i]);
            entryList.add(lrcEntry);
        }

        return entryList;
    }
}
