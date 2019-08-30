/*
 * The MIT License
 *
 * Copyright 2019 OpenNARS.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.opennars.middle.redisinput;

import org.opennars.main.Nar;
import org.opennars.plugin.Plugin;

public class RedisInput implements Plugin {
    
    String queueName = "";
    Nar nar = null;
    
    public class RedisInputThread extends Thread {
        public void run(){
            while(true) {
                try {
                    String narsese = Redis.getRedisConnection().brpop(0,queueName).get(1);
                    nar.addInput(narsese);
                }
                catch(Exception ex) {}
            }
        }
    }
    
    public RedisInput(Nar nar, String queueName, String redishost, int redisport, String redispwd) {
        this.queueName = queueName;
        this.nar = nar;
        Redis.setupRedis(redishost, redisport, redispwd); //no effect if the connection already exists
        RedisInputThread redisInThr = new RedisInputThread();
        redisInThr.start();
    }
}
