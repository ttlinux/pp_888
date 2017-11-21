package com.a8android888.bocforandroid.util;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by gd on 2016/9/1.
 */
public class ThreadTimer {

    public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static long Delay=60000;
    int  StartDelay=0;
    private  static  Handler handler=null;
    public static boolean StopALL=false;//清除全局

    public static boolean isloop=false;//是否循环
    private static OnActiviteListener mlistener;
    private static OnActiviteListener2 mlistener2;

    static int period=0;
    static boolean  TaskTag=false;


    static  long startperiod=0,endperiod=0,starttime=0,Endtime=0;

    public static void Start()
    {
        lock.writeLock().lock();
        if(mlistener==null)
        {
            lock.writeLock().unlock();
            return ;
        }
        if(TaskTag!=false && mlistener!=null)
        {
            lock.writeLock().unlock();
            return ;
        }
//        if(handler==null)
//        {
            handler=new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    switch (msg.what)
                    {
                        case 0:
                            if(TaskTag==false || mlistener==null || StopALL)
                            {
                                if(lock.writeLock().isHeldByCurrentThread())
                                lock.writeLock().unlock();
                                handler=null;
                                mlistener=null;
                                return ;
                            }
                            else
                            {
                                period=period+1000;
                                long limittime=Delay-period;
                                mlistener.MinCallback(limittime);
                                if(limittime==0)
                                {
                                    period=0;
                                    mlistener.DelayCallback();
                                    if(isloop)
                                        handler.sendEmptyMessageDelayed(0,1000);
                                    else
                                        Stop();
                                }
                                else
                                handler.sendEmptyMessageDelayed(0,1000);
                            }
                            break;
                        case 1:
                            mlistener=null;
                            TaskTag=false;
                            handler=null;
                            break;
                    }
                }
            };
//        }
        TaskTag=true;
        handler.sendEmptyMessage(0);
        lock.writeLock().unlock();
    }


    public static boolean IsRuning()
    {
        return TaskTag;
    }

    public static final void Start(final long delay)
    {
        lock.writeLock().lock();
        if(mlistener==null)
        {
            lock.writeLock().unlock();
            return ;
        }
        if(TaskTag!=false && mlistener!=null)
        {
            lock.writeLock().unlock();
            return ;
        }
//        if(handler==null)
//        {
            handler=new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    switch (msg.what)
                    {
                        case 0:
                            if(TaskTag==false || mlistener==null || StopALL)
                            {
                                if(lock.writeLock().isHeldByCurrentThread())
                                    lock.writeLock().unlock();
                                handler=null;
                                mlistener=null;
                                return ;
                            }
                            else
                            {
                                period=period+1000;
                                long limittime=Delay-period;
                                mlistener.MinCallback(limittime);
                                if(limittime==0)
                                {
                                    period=0;
                                    mlistener.DelayCallback();
                                    Stop();
                                }
                                else
                                handler.sendEmptyMessageDelayed(0,1000);
                            }
                            break;
                        case 1:
                            mlistener=null;
                            TaskTag=false;
                            handler=null;
                            break;
                    }
                }
            };
//        }
        TaskTag=true;
        handler.sendEmptyMessage(0);
        setDelay(delay);
        lock.writeLock().unlock();
    }

    public static final void Start(final long delay,long closetime,long endtime)
    {
        if(IsRuning())return;
        startperiod=0;
        endperiod=0;
        starttime=closetime;
        Endtime=endtime;
        lock.writeLock().lock();
        if(mlistener2==null)
        {
            lock.writeLock().unlock();
            return ;
        }
        if(TaskTag!=false && mlistener2!=null)
        {
            lock.writeLock().unlock();
            return ;
        }
//        if(handler==null)
//        {
        handler=new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        if(TaskTag==false || mlistener2==null || StopALL || handler==null)
                        {
                            if(lock.writeLock().isHeldByCurrentThread())
                                lock.writeLock().unlock();
                            handler=null;
                            mlistener2=null;
                            return ;
                        }
                        else
                        {
                            period=period+1000;
                            startperiod=startperiod+1000;
                            endperiod=endperiod+1000;
                            long limittime=Delay-period;
                            if(limittime==0)
                            {
                                period=0;
                                mlistener2.DelayCallback();
                            }
                            mlistener2.MinCallback(limittime,starttime-startperiod,Endtime-endperiod);
                            if(handler!=null)
                                handler.sendEmptyMessageDelayed(0,1000);
                            else
                                TaskTag=false;
                        }
                        break;
                    case 1:
                        mlistener=null;
                        TaskTag=false;
                        handler=null;
                        break;
                }
            }
        };
//        }
        TaskTag=true;
        handler.sendEmptyMessage(0);
        setDelay(delay);
        lock.writeLock().unlock();
    }

    public  static final void Stop(String...tag)
    {
//        lock.writeLock().lock();
        if (tag!=null && tag.length>0)
        {
            Log.e("Stop tag", tag[0]);
            setListener(null, tag[0]);
        }
        else
        {
            LogTools.e("Stop", "Stop");
            setListener(null);
        }
        setListener2(null);
        period=0;
        startperiod=0;
        endperiod=0;
        TaskTag=false;
        if(handler!=null) {
//            LogTools.e("handler", handler.hashCode() + "");
            handler.removeMessages(0);
//            handler.sendEmptyMessage(1);
        }
        handler=null;
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        lock.writeLock().unlock();
    }

    public interface OnActiviteListener
    {
        public void MinCallback(long period);
        public void DelayCallback();
    }
    public interface OnActiviteListener2
    {
        public void MinCallback(long period,long start,long endtime);
        public void DelayCallback();
    }
    public  long getDelay() {
        return Delay;
    }

    public  static void setDelay(long delay) {
        Delay = delay;
    }
    public  OnActiviteListener getListener() {
        return mlistener;
    }

    public static void  setListener(OnActiviteListener listener,String ...str) {
//        LogTools.e("OnActiviteListener",(str!=null && str.length>0?str[0]:"")+"    "+(listener==null?"YES":"NO"));
        mlistener = listener;
    }

    public static boolean getloop() {
        return isloop;
    }

    public static void setLoop(boolean isloop) {
        ThreadTimer.isloop = isloop;
    }

    public static void setListener2(OnActiviteListener2 mlistener2) {
        ThreadTimer.mlistener2 = mlistener2;
    }
}
