package com.yqy.myresume.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

/**
 * 语音播报引擎
 *
 * 
 */
public class SourceEngine {

	private SoundPool mSoundPool;

	private HashMap<String, Integer> mSoundPoolMap;

	private AudioManager mAudioManager;

	private Context mContext;

	private Handler mHandler = new Handler();

	private Vector<Integer> mKillSoundQueue = new Vector<Integer>();

	private long delay = 200;

	private long seperateTime = 500;

	private static SourceEngine _instance;

	private boolean isPlaying = false;
	private Map<String, String> audioFiles = new HashMap<String, String>();
	public static SourceEngine getInstance(Context context) {
		if (_instance == null)
			synchronized (SourceEngine.class) {
				if (null == _instance) {
					_instance = new SourceEngine(context);
				}
			}
		return _instance;
	}
	private SourceEngine(final Context context) {
		TaskEngine.getInstance().submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initSounds(context);
				
			}
		});
	}

	/**
	 * 初始化
	 * 
	 * @param theContext
	 */
	private void initSounds(Context theContext) {
		mContext = theContext;
		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
		mSoundPoolMap = new HashMap<String, Integer>();
		mAudioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		loadSounds();
	}

	/**
	 * 添加音频文件
	 * 
	 * @param key
	 * @param SoundID
	 */
	public void addSound(String key, int SoundID) {
		mSoundPoolMap.put(key, mSoundPool.load(mContext, SoundID, 1));
	}

	/**
	 * 添加音频文件
	 * 
	 * @param key
	 * @param afd
	 */
	public void addSound(String key, AssetFileDescriptor afd) {
		mSoundPoolMap.put(key, mSoundPool.load(afd.getFileDescriptor(),
				afd.getStartOffset(), afd.getLength(), 1));
	}

	/**
	 * 播放一个音频
	 * 
	 * @param key
	 */
	public void playSound(String key) {
		int soundId = mSoundPool.play(mSoundPoolMap.get(key), 1, 1, 1, 0, 1);
		mKillSoundQueue.add(soundId);
		// schedule the current sound to stop after set milliseconds
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (!mKillSoundQueue.isEmpty()) {
					mSoundPool.stop(mKillSoundQueue.firstElement());
				}
			}
		}, delay);
	}

	/**
	 * 播放多个音频
	 * 
	 * @param keys
	 */
	public void playMutilSounds(String keys[]) throws InterruptedException {
		if (null == keys || keys.length < 1) {
			return;
		}
		isPlaying = true;;
		for (String key : keys) {
			if (mSoundPoolMap.containsKey(key)) {
				Thread.sleep(seperateTime);
				if(isPlaying()) {
					int soundId = mSoundPool.play(mSoundPoolMap.get(key), 1, 1, 1, 0, 1);
					if ("A".equals(key)) {
						Thread.sleep(700);
					}
					mKillSoundQueue.add(soundId);
				} else {
					break;
				}
			}
		}
		isPlaying = false;
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (!mKillSoundQueue.isEmpty()) {
					mSoundPool.stop(mKillSoundQueue.firstElement());
				}
			}
		}, delay);
	}

	/**
	 * 装载音频文件
	 * 
	 * @param
	 *
	 */

	public void loadSounds() {
		AssetFileDescriptor afd;
		Map<String, String> audioFiles = new HashMap<String, String>();
		audioFiles.put("0", "0.ogg");
		audioFiles.put("1", "1.ogg");
		audioFiles.put("2", "2.ogg");
		audioFiles.put("3", "3.ogg");
		audioFiles.put("4", "4.ogg");
		audioFiles.put("5", "5.ogg");
		audioFiles.put("6", "6.ogg");
		audioFiles.put("7", "7.ogg");
		audioFiles.put("8", "8.ogg");
		audioFiles.put("9", "9.ogg");
		audioFiles.put("A", "A.ogg");
		audioFiles.put("E", "E.ogg");
		audioFiles.put("F", "F.ogg");
		audioFiles.put("shake_match","shake_match.mp3");
		audioFiles.put("shake_nomatch","shake_nomatch.mp3");
		audioFiles.put("shake_sound_male","shake_sound_male.mp3");
		try {
			for (String key : audioFiles.keySet()) {
				String[] paths = mContext.getAssets().list("audio");
				for(String path : paths) {
					if("3".equals(key)&&path.endsWith(".mp3"))continue;
					if(path.contains(key)) {
						afd = mContext.getAssets().openFd("audio/" + path);
						addSound(key, afd);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止播放制定的音频
	 * 
	 * @param index
	 *            - index of the sound to be stopped
	 */
	public void stopSound(int index) {
		mSoundPool.stop(mSoundPoolMap.get(index));
	}

	/**
	 * 停止播放所有音频
	 */
	public void stopSound() {
		try {
			isPlaying = false;
			if(null != mKillSoundQueue) {
				int size = mKillSoundQueue.size();
				for(int i = 0; i < size; i++) {
					mSoundPool.stop(mKillSoundQueue.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 销毁SoundPool
	 */
	public void cleanup() {
		if (mSoundPool != null) {
			mSoundPool.release();
			mSoundPool = null;
		}
		if (mSoundPoolMap != null) {
			mSoundPoolMap.clear();
			mAudioManager.unloadSoundEffects();
			mSoundPoolMap = null;
			mAudioManager = null;
		}
		_instance = null;

	}

	/**
	 * 去除所有播放源 support for user change VoiceLanguage or Locale or user close the
	 * voice function !
	 */
	public void unloadAllSoundsIn() {
		if(null != mSoundPoolMap) {
			if (mSoundPoolMap.size() > 0) {
				for (String key : mSoundPoolMap.keySet()) {
					mSoundPool.unload(mSoundPoolMap.get(key));
				}
			}
			mKillSoundQueue.clear();
			mSoundPoolMap.clear();
		}
	}

	/**
	 * 是否正在播放
	 * 
	 * @return 【true:播放】【false:停止】
	 */
	public boolean isPlaying() {
		return isPlaying;
	}
	
	/**
	 * 设置是否播放
	 * @param isPlaying【true:播放】【false:停止】
	 */
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
