package assignments.project;

public class WebCrawler {

    public static void main(String[] args) {
//        String source; link of page to crawl

//        Thread t1 = new Thread(new Executor(queue));
//        Thread t2 = new Thread(new Executor(queue));
//        Thread t3 = new Thread(new Executor(queue));

//        t1.start();
//        t2.start();
//        t3.start();

    }

//    Milestone 1
//    Crawler that will crawl Touro's website and learn interesting information about it.
//    Note these two VERY IMPORTANT points:
//      • You may not download ANY page that is not in the touro.edu domain.
//      • You will have ONE thread that is dedicated to downloading pages from within the touro.edu
//        domain, and it will always pause between downloading pages. It will remember the time
//        elapsed from the moment it starts downloading a webpage and moment the download is
//        complete, and then it will pause either twice as long as the amount of time required by the
//        previous download, or ten seconds, whichever is greater.
//    The framework of your program is as follows:
//      • You will have one thread that crawls the site in a BFS manner, starting from www.touro.edu. It
//        will download a webpage using the next (internal) URL in a queue dedicated to holding the URLs
//        to crawl, and then put the html in another queue.
//      • You will have 2 threads that will do the actual scraping. They will take the next html String off of
//        the queue and extract all of the useful information, including internal URLs which, in addition to
//        being tracked for informational purposes, need to be added to the crawler's queue. Remember
//        to lock carefully, there is a lot of shared memory here.
//      • Finally, you will have one thread for the UI. This thread can be the main thread, after it has
//        started the other threads. This thread should allow the user to check the progress of the crawler
//        and view the most current results. I will leave the exact details up to you, but the basic idea is
//        that the user has to be able to view URLs, emails, etc. and also know how many pages have been
//        collected, how many more are in the queue, etc.


//    thread
//    1: crawl the site in a BFS manner, starting from www.touro.edu
//       download the next webpage from queue 1
//       put html in queue 2
//    2: get the next html from queue 2
//       extract useful information:
//          internal URLs for info and added to queue 1 (lock threads carefully with shared memory)
//    3: thread for UI
//       allow the user to check progress of the crawler and view current results
//       view URLs, emails, number of pages collected, number of pages left in the queue, etc.
//    queue
//    1: hold urls to be downloaded
//    2: hold html to be explored

}
