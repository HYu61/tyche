package pers.hyu.tyche.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The util is for editing the video.
 * Include remove audio of the video, combine other audio with the video, and generate the image from the video.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class FfmpegUtil {
    private String exeLocation; // the path of the exe file

    public FfmpegUtil(String exeLocation) {
        this.exeLocation = exeLocation;
    }

    /**
     * Remove the original audio from the video.
     *
     * @param inputVideoPath   The path of the video need to be edited.
     * @param outputVideoVideo The path of the video removed the audio.
     * @throws IOException
     */
    public void removeAudio(String inputVideoPath, String outputVideoVideo) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add(exeLocation);
        commands.add("-i");
        commands.add(inputVideoPath);
        commands.add("-vcodec");
        commands.add("copy");
        commands.add("-an");
        commands.add(outputVideoVideo);

        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();
        this.release(process);
    }

    /**
     * Combine the video with the background music.
     *
     * @param inputVideoPath  The path of the video need to be edit.
     * @param inputBgmPath    The path of the bgm need to be added.
     * @param duration        The seconds of the video.
     * @param outputVideoPath The path of the combined video.
     * @throws IOException
     */
    public void combineVideoWithBgm(String inputVideoPath, String inputBgmPath, float duration, String outputVideoPath) throws IOException {
        // ffmpeg -i input.mp4 -stream_loop -1 -i input.mp3 -ss 0 -t 17 -y output.mp4  ----bgm loop
        List<String> commands = new ArrayList<>();
        commands.add(exeLocation);
        commands.add("-i");
        commands.add(inputVideoPath);
        commands.add("-stream_loop");
        commands.add("-1");
        commands.add("-i");
        commands.add(inputBgmPath);
        commands.add("-ss");
        commands.add("0");
        commands.add("-t");
        commands.add(String.valueOf(duration));
        commands.add("-y");
        commands.add(outputVideoPath);

        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();

        this.release(process);
    }

    /***
     * Generate the image from the first frame of the video.
     *
     * @param inputVideoPath  The path of the video.
     * @param outputCoverPath The path for save the generated image.
     * @throws IOException
     */
    public void generateCover(String inputVideoPath, String outputCoverPath) throws IOException {
        // ffmpeg -ss 00:00:11 -y -i 1.mp4 -vframes 1 new.jpg
        List<String> commands = new ArrayList<>();
        commands.add(exeLocation);
        commands.add("-ss");
        commands.add("00:00:01");
        commands.add("-y");
        commands.add("-i");
        commands.add(inputVideoPath);
        commands.add("-vframes");
        commands.add("1");
        commands.add(outputCoverPath);

        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();

        this.release(process);
    }

    /**
     * Release the resources of the process.
     *
     * @param process The process which is processing.
     * @throws IOException
     */
    private void release(Process process) throws IOException {
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }

        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }
}
