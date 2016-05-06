package workshop.client.manager;

public interface IController {
    
    /**
     * Gets shedule.
     * @return list of times when workshop is busy.
     */
    public String[] getShedule();

    public String getInfo(String time);
}
