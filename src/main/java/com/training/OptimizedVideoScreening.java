package com.training;

/**
 * OptimizedVideoScreening
 * A learner is given a sequential playlist of n videos to enhance their knowledge.
 * Implement a function to calculate the minimum time required to complete a total of m
 * viewings while adhering to the platform's constraints.
 * System Constraints
 * • Sequential Viewing:
*    o The learner must watch videos in the order they appear in the playlist.
*    o To watch a video at index i, the learner must first watch all videos from
*         index 0 to i-1 at least once.
 * • Full Viewing:
 *   o To view any video at index i for the rst time, the learner must
 *   spend rstWatch[i] + repeatWatch[i] minutes. This ensures a complete
 *   understanding of the video.
 * • Subsequent Viewings:
 *   o For subsequent viewings of a video at index i, the learner spends
 *     only repeatWatch[i] minutes.
 *   o At any time, they can rewatch any video they have already watched.
 * • Minimum Total Watch Count:
 *   o The learner must achieve at least m total viewings across all videos.
 *   o Videos can be rewatched as desired, and not all videos need to be
 * watched equally.
 * Return the minimum time required to complete m viewings of videos while following
 * these constraints.
 * 
 * Example
 * If n = 4, m = 4, rstWatch = [1, 5, 9, 11], and repeatWatch = [2, 7, 10, 11], the optimal
 * strategy is as follows:
 * • The learner watches the 0th video for the rst time, spending rstWatch[0]
 * + repeatWatch[0] = 1 + 2 = 3 minutes.
 * • The learner then rewatches the 0th video 3 more times in,
 * spending repeatWatch[0]*3 = 2 * 3 = 6 minutes.
 * • The learner has successfully completed 4 viewings. Thus, the total time required
 * is 3 + 6 = 9 minutes, and 9 is returned.
 * Function Description
 * Complete the function findMinMinutes in the editor below.
 * Function Parameters
 * int m: the total number of video viewings to complete
 * int firstWatch[n]: the minutes required to watch each video the rst time
 * int repeatWatch[n]: the minutes required to rewatch each video
 * Returns
 * long: the minimum minutes required for the learner to reach their target number of
 * viewings
 * Constraints
 * • 1 ≤ n ≤ 105
 * • 1 ≤ m ≤ 109
 * • 1 ≤ rstWatch[i], repeatWatch[i] ≤ 109
 * Sample Input 0
STDIN FUNCTION
----- --------
5
m=5
3
rstWatch[] size n = 3
6
rstWatch = [6, 8, 9]
8
9
3
4
2
10
repeatWatch[] size n = 3
repeatWatch = [4, 2, 10]
Sample Output 0
26
Explanation
The goal is to complete 5 viewings. One optimal strategy is:
• The learner watches the 0th video for the rst time, so the time required
is rstWatch[0] + repeatWatch[0] = 6 + 4 = 10 minutes.
• The learner watches the 1st video for the rst time, so the time required
is rstWatch[1] + repeatWatch[1] = 8 + 2 = 10 minutes.
• The learner then rewatches the 1st video 3 times. So the time taken
= repeatWatch[1]*3 = 2 * 3 = 6 minutes.
• The total time required to complete 5 video viewings is 10 + 10 + 6 = 26 minutes.
Sample Case 1
Sample Input 1
STDIN FUNCTION
----- --------
6
m=6
5
rstWatch[] size n = 5
2
rstWatch = [2, 3, 7, 8, 4]
3
7
8
4
5
repeatWatch[] size n = 5
3
repeatWatch = [3, 6, 8, 7, 6]
68
7
6
Sample Output 1
20
Explanation
The goal is to complete 6 viewings. One optimal strategy is:
• The learner watches the 0th video for the rst time in rstWatch[0]
+ repeatWatch[0] = 2 + 3 = 5 minutes.
• The learner then rewatches the 0th video 5 times in repeatWatch[0]*5 = 3 * 5 = 15
minutes.
• The total time required to complete 6 viewings is 5 + 15 = 20 minutes.
 */


public class OptimizedVideoScreening {

    public static void main(String[] args) {
        int firstWatch[] = {2, 3, 7, 8, 4};
        int repeatWatch[] = {3, 6, 8, 7, 6};
        int m = 6;

        System.out.println(findMinMinutes(m, firstWatch, repeatWatch));

    }

    public static long findMinMinutes(int m, int[] firstWatch, int[] repeatWatch) {

        long firstWatchTimeUntilK = 0;
        long minRepeatWatchTime = Long.MAX_VALUE;
        long minTime = Long.MAX_VALUE;

        for(int k = 1; k < firstWatch.length; k++) {
            int i = k - 1;
            firstWatchTimeUntilK += firstWatch[i] + repeatWatch[i];
            minRepeatWatchTime = Math.min(minRepeatWatchTime, repeatWatch[i]);
            int remainingViews = m - k;
            long timeUntilK = firstWatchTimeUntilK + remainingViews * minRepeatWatchTime;
            minTime = Math.min(minTime, timeUntilK);
        }


        return minTime;
    }

}
