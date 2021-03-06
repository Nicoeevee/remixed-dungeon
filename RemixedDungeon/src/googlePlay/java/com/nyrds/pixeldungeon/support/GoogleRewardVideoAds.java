package com.nyrds.pixeldungeon.support;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.nyrds.pixeldungeon.ml.EventCollector;
import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.noosa.InterstitialPoint;

import androidx.annotation.MainThread;

/**
 * Created by mike on 30.01.2017.
 * This file is part of Remixed Pixel Dungeon.
 */

public class GoogleRewardVideoAds implements AdsUtilsCommon.IRewardVideoProvider {

	private static final String GOOGLE_REWARD_VIDEO = "google reward video";
	private static RewardedVideoAd mCinemaRewardAd;
	private static InterstitialPoint returnTo;


	public GoogleRewardVideoAds() {
		Game.instance().runOnUiThread(this::loadNextVideo);
	}


	@MainThread
	private void loadNextVideo() {
		EventCollector.startTrace(GOOGLE_REWARD_VIDEO);

		mCinemaRewardAd = MobileAds.getRewardedVideoAdInstance(Game.instance());
		mCinemaRewardAd.setRewardedVideoAdListener(new RewardVideoAdListener());
		mCinemaRewardAd.loadAd(Game.getVar(R.string.cinemaRewardAdUnitId), AdMob.makeAdRequest());
	}

	@MainThread
	public  boolean isReady() {
		return mCinemaRewardAd != null && mCinemaRewardAd.isLoaded();
	}

	@Override
	public void showRewardVideo(InterstitialPoint ret) {
		returnTo = ret;
		mCinemaRewardAd.show();
	}

	private class RewardVideoAdListener implements RewardedVideoAdListener {

		private boolean videoCompleted = false;

		@Override
		public void onRewardedVideoAdLoaded() {
			EventCollector.stopTrace(GOOGLE_REWARD_VIDEO, GOOGLE_REWARD_VIDEO,"ok","");
			videoCompleted = false;
		}

		@Override
		public void onRewardedVideoAdOpened() { }

		@Override
		public void onRewardedVideoStarted() { }

		@Override
		public void onRewardedVideoAdClosed() {
			Game.instance().runOnUiThread(GoogleRewardVideoAds.this::loadNextVideo);
			Game.pushUiTask(() -> returnTo.returnToWork(videoCompleted));
		}

		@Override
		public void onRewarded(RewardItem rewardItem) {
			videoCompleted = true;
		}

		@Override
		public void onRewardedVideoAdLeftApplication() {
		}

		@Override
		public void onRewardedVideoAdFailedToLoad(int i) {
			EventCollector.stopTrace(GOOGLE_REWARD_VIDEO, GOOGLE_REWARD_VIDEO,"fail","");
		}

		@Override
		public void onRewardedVideoCompleted() {
			videoCompleted = true;
		}
	}

}
